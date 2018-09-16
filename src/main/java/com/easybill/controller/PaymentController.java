package com.easybill.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easybill.config.security.CurrentUser;
import com.easybill.config.security.UserPrincipal;
import com.easybill.exception.EntityExistsException;
import com.easybill.exception.EntityNotFoundException;
import com.easybill.exception.ValidationException;
import com.easybill.pojo.PaymentForm;
import com.easybill.service.PaymentService;
import com.easybill.util.Constants;
import com.easybill.util.ResponseUtil;
import com.easybill.validation.ValidationUtil;

@RestController
@RequestMapping(value = "/payment")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;

	@PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
	@Secured({ Constants.ROLE_WHOLESALER })
	public ResponseEntity<String> addPayment(@CurrentUser UserPrincipal currentUser,
			@RequestBody @Validated PaymentForm paymentForm, Errors result)
			throws ValidationException, EntityExistsException, EntityNotFoundException {
		ValidationUtil.checkValidationErrors(result);
		paymentService.addPayment(currentUser.getId(), paymentForm);
		return ResponseUtil.buildSuccessResponseEntity("Payment added successfully");
	}

	@PutMapping(value = "/approve/{paymentId}")
	@Secured({ Constants.ROLE_DISTRIBUTOR })
	public ResponseEntity<String> approveOrder(@CurrentUser UserPrincipal currentUser,
			@PathVariable("paymentId") Integer paymentId) throws EntityNotFoundException {
		paymentService.approvePayment(currentUser.getId(), paymentId);
		return ResponseUtil.buildSuccessResponseEntity("Payment is approved");
	}

}
