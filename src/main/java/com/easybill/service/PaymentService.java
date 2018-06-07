package com.easybill.service;

import com.easybill.exception.EntityExistsException;
import com.easybill.exception.EntityNotFoundException;
import com.easybill.exception.ValidationException;
import com.easybill.model.PaymentInformation;
import com.easybill.pojo.PaymentForm;

public interface PaymentService {
	
	void addPayment(Integer userId, PaymentForm paymentForm) throws ValidationException, EntityExistsException, EntityNotFoundException;

	void approveOrder(Integer userId, Integer paymentId) throws EntityNotFoundException;

	PaymentInformation getById(Integer paymentId) throws EntityNotFoundException;

}
