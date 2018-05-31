package com.easybill.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easybill.exception.EntityNotFoundException;
import com.easybill.model.Item;
import com.easybill.model.OrderDetail;
import com.easybill.model.OrderInfo;
import com.easybill.model.User;
import com.easybill.model.metadata.EnumConstant.Status;
import com.easybill.pojo.OrderDetailVO;
import com.easybill.pojo.OrderVO;
import com.easybill.repository.OrderInfoRepository;
import com.easybill.service.ItemService;
import com.easybill.service.OrderService;
import com.easybill.service.UserService;
import com.easybill.util.CommonUtil;
import com.easybill.util.DateUtil;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderInfoRepository orderInfoRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired 
	private ItemService itemService;

	@Override
	public void addOrder(int userId, OrderVO orderVO) throws EntityNotFoundException {
		OrderInfo orderInfo = new OrderInfo();
		setValues(orderInfo, userId, orderVO);
		orderInfoRepository.save(orderInfo);
	}
	
	private void setValues(OrderInfo orderInfo, int userId, OrderVO orderVO) throws EntityNotFoundException {
		User user = userService.getUserById(userId);
		orderInfo.setDescription(orderVO.getDescription());
		orderInfo.setOrderDate(DateUtil.getCurrentTime());
		orderInfo.setStatus(Status.ACTIVE);
		orderInfo.setOrderedBy(user);
		
		for (OrderDetailVO detailVO : orderVO.getOrderDetails()) {
			orderInfo.getOrderDetails().add(mapToOrderDetail(detailVO, orderInfo));
		}
	}

	private OrderDetail mapToOrderDetail(OrderDetailVO detailVO, OrderInfo orderInfo) throws EntityNotFoundException{
		Item item = itemService.getItemById(detailVO.getItemId());
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setOrderInfo(orderInfo);
		orderDetail.setQuantity(detailVO.getQuantity());
		orderDetail.setUnit(CommonUtil.getUnit(detailVO.getUnit()));
		orderDetail.setItem(item);
		return orderDetail;
	}

	@Override
	public void editOrder(Integer userId, OrderVO orderVO) throws EntityNotFoundException {
		OrderInfo orderInfo = getOrderById(orderVO.getId());
		User user = userService.getUserById(userId);
		orderInfo.setModifiedBy(user);
		orderInfo.setModifiedAt(DateUtil.getCurrentTime());
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
		orderInfo.getOrderDetails().forEach(orderDetail -> {
			System.out.println(orderDetail.getQuantity());
		});;
		return null;
	}

	@Override
	public List<OrderVO> getAllOrders() {
		List<OrderVO> orders = new ArrayList<>();
		orderInfoRepository.findAll().forEach(orderInfo -> {
			orders.add(mapToOrderVO(orderInfo));
		});
		return orders;
	}

	private OrderVO mapToOrderVO(OrderInfo orderInfo) {
		OrderVO orderVO = new OrderVO();
		orderVO.setId(orderInfo.getId());
		orderVO.setDescription(orderInfo.getDescription());
		List<OrderDetailVO> orderDetails = new ArrayList<>();
		orderInfo.getOrderDetails().forEach(orderDetail -> {
			orderDetails.add(mapToOrderDetailVO(orderDetail));
		});
		orderVO.setOrderDetails(orderDetails);
		return orderVO;
	}

	private OrderDetailVO mapToOrderDetailVO(OrderDetail orderDetail) {
		OrderDetailVO detailVO = new OrderDetailVO();
		detailVO.setId(orderDetail.getId());
		detailVO.setQuantity(orderDetail.getQuantity());
		detailVO.setItemId(orderDetail.getItem().getId());
		detailVO.setUnit(orderDetail.getUnit().name());
		return detailVO;
	}
}
