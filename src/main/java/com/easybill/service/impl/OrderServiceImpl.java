package com.easybill.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easybill.exception.BillAmountNotPaidException;
import com.easybill.exception.EntityExistsException;
import com.easybill.exception.EntityNotFoundException;
import com.easybill.exception.OrderAlreadyApprovedException;
import com.easybill.exception.ValidationException;
import com.easybill.model.BillInformation;
import com.easybill.model.Item;
import com.easybill.model.OrderDetail;
import com.easybill.model.OrderInfo;
import com.easybill.model.User;
import com.easybill.model.metadata.EnumConstant.Unit;
import com.easybill.pojo.EditOrderVO;
import com.easybill.pojo.OrderDetailVO;
import com.easybill.pojo.OrderVO;
import com.easybill.repository.OrderDetailRepository;
import com.easybill.repository.OrderInfoRepository;
import com.easybill.service.BillService;
import com.easybill.service.ItemService;
import com.easybill.service.OrderService;
import com.easybill.service.UserService;
import com.easybill.util.CommonUtil;
import com.easybill.util.DateUtil;
import com.easybill.util.ExceptionMessage;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderInfoRepository orderInfoRepository;
	
	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private ItemService itemService;

	@Autowired
	private BillService billService;

	@Override
	public void addOrder(int userId, OrderVO orderVO) throws EntityNotFoundException, ValidationException {
		OrderInfo orderInfo = new OrderInfo();
		setValues(orderInfo, userId, orderVO);
		orderInfoRepository.save(orderInfo);
	}

	private void setValues(OrderInfo orderInfo, int userId, OrderVO orderVO)
			throws EntityNotFoundException, ValidationException {
		User user = userService.getUserById(userId);
		orderInfo.setDescription(orderVO.getDescription());
		orderInfo.setOrderedAt(DateUtil.getCurrentTime());
		orderInfo.setOrderedBy(user);

		for (OrderDetailVO detailVO : orderVO.getOrderDetails()) {
			orderInfo.getOrderDetails().add(mapToOrderDetail(detailVO, orderInfo));
		}
	}

	private OrderDetail mapToOrderDetail(OrderDetailVO detailVO, OrderInfo orderInfo)
			throws EntityNotFoundException, ValidationException {
		Item item = itemService.getItemById(detailVO.getItemId());
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setOrderInfo(orderInfo);
		orderDetail.setQuantity(detailVO.getQuantity());
		Unit unit = CommonUtil.getUnit(detailVO.getUnit());
		// If unit selected for item is not base or large unit of the item
		if (!item.isValidUnit(unit)) {
			throw new ValidationException(ExceptionMessage.INVALID_UNIT_EXCEPTION_MESSAGE + " for " + item.getName());
		}
		orderDetail.setUnit(unit);
		orderDetail.setItem(item);
		return orderDetail;
	}

	@Override
	public void editOrder(Integer userId, EditOrderVO editOrderVO)
			throws EntityNotFoundException, OrderAlreadyApprovedException, ValidationException, EntityExistsException {
		OrderInfo orderInfo = getOrderById(editOrderVO.getId());
		if (orderInfo.isApproved()) {
			throw new OrderAlreadyApprovedException("Order is already approved: " + orderInfo.getId());
		}
		User user = userService.getUserById(userId);
		orderInfo.setModifiedBy(user);
		orderInfo.setModifiedAt(DateUtil.getCurrentTime());
		orderInfo.setDescription(editOrderVO.getDescription());
		
		List<Integer> editedOrderDetailIdList = new ArrayList<>(editOrderVO.getOrderDetails().keySet());
		if (!editedOrderDetailIdList.isEmpty()) {
			List<OrderDetail> orderDetails = orderDetailRepository.findByIdIn(editedOrderDetailIdList);
			for (OrderDetail orderDetail : orderDetails) {
				OrderDetailVO detailVO = editOrderVO.getOrderDetails().get(orderDetail.getId());
				orderDetail.setQuantity(detailVO.getQuantity());
				orderDetail.setUnit(CommonUtil.getUnit(detailVO.getUnit()));
			}
			orderDetailRepository.saveAll(orderDetails);
		}
		
		if (Objects.nonNull(editOrderVO.getDeletedItemList())
				&& !editOrderVO.getDeletedItemList().isEmpty()) {
			orderDetailRepository.deleteByIdIn(editOrderVO.getDeletedItemList());
		}
		orderInfoRepository.save(orderInfo);
		
		// Approve the order if user rank is more than the user who placed the order
		if (editOrderVO.isApprove() && user.canApproveOrder(orderInfo.getOrderedBy())) {
			approveOrder(editOrderVO.getId());
		}
	}

	@Override
	public void approveOrder(Integer orderId)
			throws EntityNotFoundException, ValidationException, OrderAlreadyApprovedException, EntityExistsException {
		OrderInfo orderInfo = getOrderById(orderId);
		if (orderInfo.isApproved()) {
			throw new OrderAlreadyApprovedException("Order is already approved: " + orderInfo.getId());
		}
		orderInfo.setApproved(true);
		billService.saveBill(orderInfo);
		orderInfoRepository.save(orderInfo);
	}

	@Override
	public OrderInfo getOrderById(Integer orderId) throws EntityNotFoundException {
		return orderInfoRepository.findById(orderId)
				.orElseThrow(() -> new EntityNotFoundException("Order could not be found with id: " + orderId));
	}

	@Override
	public OrderVO getOrderDetailsById(Integer orderId) throws EntityNotFoundException {
		OrderInfo orderInfo = getOrderById(orderId);
		return mapToOrderVO(orderInfo, true);
	}

	@Override
	public List<OrderVO> getAllOrders() {
		List<OrderVO> orders = new ArrayList<>();
		orderInfoRepository.findAll().forEach(orderInfo -> {
			orders.add(mapToOrderVO(orderInfo, false));
		});
		return orders;
	}

	/**
	 * Map orderInfo to orderVO.
	 * 
	 * @param orderInfo
	 * @param detailView
	 * @return orderVO
	 */
	private OrderVO mapToOrderVO(OrderInfo orderInfo, boolean detailView) {
		OrderVO orderVO = new OrderVO();
		orderVO.setId(orderInfo.getId());
		orderVO.setDescription(orderInfo.getDescription());
		orderVO.setOrderedAt(
				DateUtil.formatDateToString(orderInfo.getOrderedAt(), DateUtil.DD_MMM_YYYY_HH_MM_SS_A_FORMAT));
		orderVO.setApproved(orderInfo.isApproved());
		if (detailView) {
			List<OrderDetailVO> orderDetails = new ArrayList<>();
			orderInfo.getOrderDetails().forEach(orderDetail -> {
				orderDetails.add(mapToOrderDetailVO(orderDetail));
			});
			orderVO.setOrderDetails(orderDetails);

			BillInformation billInformation = orderInfo.getBillInformation();
			if (Objects.nonNull(billInformation)) {
				orderVO.setAmount(billInformation.getAmount());
				orderVO.setPendingAmount(billInformation.getPendingAmount());
				orderVO.setBilledAt(DateUtil.formatDateToString(billInformation.getBilledAt(),
						DateUtil.DD_MMM_YYYY_HH_MM_SS_A_FORMAT));
			}
		}
		return orderVO;
	}

	/**
	 * Map orderDetail to orderDetailVO
	 * 
	 * @param orderDetail
	 * @return orderDetailVO
	 */
	private OrderDetailVO mapToOrderDetailVO(OrderDetail orderDetail) {
		OrderDetailVO detailVO = new OrderDetailVO();
		detailVO.setId(orderDetail.getId());
		detailVO.setQuantity(orderDetail.getQuantity());
		detailVO.setItemId(orderDetail.getItem().getId());
		detailVO.setItemName(orderDetail.getItem().getName());
		detailVO.setUnit(orderDetail.getUnit().name());
		return detailVO;
	}

	@Override
	public void deleteOrderById(Integer orderId) throws EntityNotFoundException, BillAmountNotPaidException {
		OrderInfo orderInfo = getOrderById(orderId);
		orderInfo.isBillAmountPaid();
		orderInfoRepository.delete(orderInfo);
	}
}