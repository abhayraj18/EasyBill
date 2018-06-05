package com.easybill.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easybill.exception.EntityExistsException;
import com.easybill.exception.ValidationException;
import com.easybill.model.BillInformation;
import com.easybill.model.Item;
import com.easybill.model.OrderDetail;
import com.easybill.model.OrderInfo;
import com.easybill.model.metadata.EnumConstant.Unit;
import com.easybill.repository.BillInformationRepository;
import com.easybill.service.BillService;
import com.easybill.util.DateUtil;
import com.easybill.util.ExceptionMessage;

@Service
public class BillServiceImpl implements BillService {
	
	@Autowired 
	private BillInformationRepository billInformationRepository;

	@Override
	public void saveBill(OrderInfo orderInfo) throws ValidationException, EntityExistsException {
		if (Objects.nonNull(orderInfo.getBillInformation())) {
			throw new EntityExistsException("Bill already exists for order with id: " + orderInfo.getId());
		}
		BillInformation billInformation = new BillInformation();
		billInformation.setBilledAt(DateUtil.getCurrentTime());
		billInformation.setAmount(calculateBillAmount(orderInfo.getOrderDetails()));
		billInformation.setPendingAmount(billInformation.getAmount());
		orderInfo.setBillInformation(billInformation);
		billInformation.setOrderInfo(orderInfo);
		billInformationRepository.save(billInformation);
	}

	private Float calculateBillAmount(List<OrderDetail> orderDetails) throws ValidationException {
		float amount = 0.0f;
		for (OrderDetail orderDetail : orderDetails) {
			Item item = orderDetail.getItem();
			switch (orderDetail.getUnit()) {
			case PC: if (item.getBaseUnit() == Unit.PC) {
						amount += item.getBaseUnitPrice() * orderDetail.getQuantity();
					 } else if (item.getLargeUnit() == Unit.PC) {
						amount += item.getLargeUnitPrice() * orderDetail.getQuantity();
					 }
				break;
			case BOX: if (item.getBaseUnit() == Unit.BOX) {
						  amount += item.getBaseUnitPrice() * orderDetail.getQuantity();
					  } else if (item.getLargeUnit() == Unit.BOX) {
						  amount += item.getLargeUnitPrice() * orderDetail.getQuantity();
					  }
				break;
			case BAG: if (item.getBaseUnit() == Unit.BAG) {
						  amount += item.getBaseUnitPrice() * orderDetail.getQuantity();
					  } else if (item.getLargeUnit() == Unit.BAG) {
						  amount += item.getLargeUnitPrice() * orderDetail.getQuantity();
					  }
				break;
			default: throw new ValidationException(ExceptionMessage.INVALID_UNIT_EXCEPTION_MESSAGE);
			}
		}
		return amount;
	}

}
