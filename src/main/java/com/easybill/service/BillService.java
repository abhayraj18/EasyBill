package com.easybill.service;

import com.easybill.exception.EntityExistsException;
import com.easybill.exception.ValidationException;
import com.easybill.model.OrderInfo;

public interface BillService {
	
	void saveBill(OrderInfo orderInfo) throws ValidationException, EntityExistsException;

}
