package com.easybill.service;

import com.easybill.exception.EntityExistsException;
import com.easybill.exception.EntityNotFoundException;
import com.easybill.exception.ValidationException;
import com.easybill.model.BillInformation;
import com.easybill.model.OrderInfo;

public interface BillService {
	
	void saveBill(OrderInfo orderInfo) throws ValidationException, EntityExistsException;

	BillInformation getById(Integer billId) throws EntityNotFoundException;

	void updateBill(BillInformation billInformation, Float amount);

	Float getExcessAmount(Integer userId);

	void resetExcessAmount(Integer userId);

}
