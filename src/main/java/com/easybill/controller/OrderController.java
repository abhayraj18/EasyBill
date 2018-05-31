package com.easybill.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easybill.config.security.CurrentUser;
import com.easybill.config.security.UserPrincipal;
import com.easybill.exception.EntityExistsException;
import com.easybill.exception.EntityNotFoundException;
import com.easybill.exception.ValidationException;
import com.easybill.pojo.OrderVO;
import com.easybill.service.OrderService;
import com.easybill.util.CommonUtil;
import com.easybill.util.Constants;
import com.easybill.util.ResponseUtil;
import com.easybill.validation.ValidationUtil;

@RestController
@RequestMapping(value = "/order")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
	@Secured(Constants.ROLE_WHOLESALER)
	public ResponseEntity<String> addOrder(@CurrentUser UserPrincipal currentUser,
			@RequestBody @Validated OrderVO orderVO, Errors result)
			throws EntityNotFoundException, ValidationException, EntityExistsException {
		if (result.hasErrors()) {
			Map<String, List<String>> errorMap = ValidationUtil.getErrorMessages(result);
			throw new ValidationException(CommonUtil.convertToJSONString(errorMap));
		}
		orderService.addOrder(currentUser.getId(), orderVO);
		return ResponseUtil.buildSuccessResponseEntity("Order placed successfully");
	}
	
	@PostMapping(value = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
	@Secured({ Constants.ROLE_DISTRIBUTOR, Constants.ROLE_WHOLESALER })
	public ResponseEntity<String> editOrder(@CurrentUser UserPrincipal currentUser,
			@RequestBody @Validated OrderVO orderVO, Errors result)
			throws EntityNotFoundException, ValidationException, EntityExistsException {
		if (result.hasErrors()) {
			Map<String, List<String>> errorMap = ValidationUtil.getErrorMessages(result);
			throw new ValidationException(CommonUtil.convertToJSONString(errorMap));
		}
		orderService.editOrder(currentUser.getId(), orderVO);
		return ResponseUtil.buildSuccessResponseEntity("Order edited successfully");
	}
	
	@GetMapping("/get/{orderId}")
	@Secured({ Constants.ROLE_DISTRIBUTOR, Constants.ROLE_WHOLESALER })
	public ResponseEntity<String> getItem(@PathVariable("orderId") Integer orderId) throws EntityNotFoundException {
		OrderVO orderVO = orderService.getOrderDetailsById(orderId);
		return ResponseUtil.buildSuccessResponseEntity(CommonUtil.convertToJSONString(orderVO));
	}
	
	@GetMapping("/getAll")
	@Secured(Constants.ROLE_WHOLESALER)
	public ResponseEntity<String> getAllOrders() {
		return ResponseUtil.buildSuccessResponseEntity(CommonUtil.convertToJSONString(orderService.getAllOrders()));
	}
	
}
