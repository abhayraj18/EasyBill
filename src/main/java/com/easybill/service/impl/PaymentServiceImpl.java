package com.easybill.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easybill.exception.EntityExistsException;
import com.easybill.exception.EntityNotFoundException;
import com.easybill.exception.ValidationException;
import com.easybill.model.BillInformation;
import com.easybill.model.PaymentInformation;
import com.easybill.model.User;
import com.easybill.pojo.PaymentForm;
import com.easybill.repository.PaymentInformationRepository;
import com.easybill.service.BillService;
import com.easybill.service.PaymentService;
import com.easybill.service.UserService;
import com.easybill.util.DateUtil;

@Service
public class PaymentServiceImpl implements PaymentService {
	
	@Autowired
	private BillService billService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PaymentInformationRepository paymentInformationRepository;
	
	@Override
	public void addPayment(Integer userId, PaymentForm paymentForm) throws ValidationException, EntityExistsException, EntityNotFoundException {
		BillInformation billInformation = billService.getById(paymentForm.getBillId());
		PaymentInformation paymentInformation = new PaymentInformation();
		paymentInformation.setBillInformation(billInformation);
		paymentInformation.setDescription(paymentForm.getDescription());
		paymentInformation.setAmount(paymentForm.getAmount());
		paymentInformation.setPaidAt(DateUtil.getCurrentTime());
		if (paymentForm.isApprove()) {
			User user = userService.getUserById(userId);
			if (user.canApprove(billInformation.getOrderInfo().getOrderedBy())) {
				paymentInformation.approve(user);
			}
		}
		paymentInformationRepository.save(paymentInformation);
		// Update bill pending/excess amount
		billService.updateBill(billInformation, paymentInformation.getAmount());
	}

	@Override
	public void approveOrder(Integer userId, Integer paymentId) throws EntityNotFoundException {
		PaymentInformation paymentInformation = getById(paymentId);
		if (!paymentInformation.isApproved()) {
			User user = userService.getUserById(userId);
			paymentInformation.approve(user);
		}
	}

	@Override
	public PaymentInformation getById(Integer paymentId) throws EntityNotFoundException {
		return paymentInformationRepository.findById(paymentId)
				.orElseThrow(() -> new EntityNotFoundException("Payment could not be found with id: " + paymentId));
	}

}
