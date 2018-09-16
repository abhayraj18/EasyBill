package com.easybill.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easybill.config.security.CurrentUser;
import com.easybill.config.security.UserPrincipal;
import com.easybill.exception.AlreadyApprovedException;
import com.easybill.exception.BillAmountNotPaidException;
import com.easybill.exception.EntityExistsException;
import com.easybill.exception.EntityNotFoundException;
import com.easybill.exception.ValidationException;
import com.easybill.pojo.EditOrderVO;
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
	@Secured({ Constants.ROLE_WHOLESALER })
	public ResponseEntity<String> addOrder(@CurrentUser UserPrincipal currentUser,
			@RequestBody @Validated OrderVO orderVO, Errors result)
			throws EntityNotFoundException, ValidationException, EntityExistsException {
		ValidationUtil.checkValidationErrors(result);
		orderService.addOrder(currentUser.getId(), orderVO);
		return ResponseUtil.buildSuccessResponseEntity("Order placed successfully");
	}

	@PostMapping(value = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
	@Secured({ Constants.ROLE_DISTRIBUTOR, Constants.ROLE_WHOLESALER })
	public ResponseEntity<String> editOrder(@CurrentUser UserPrincipal currentUser,
			@RequestBody @Validated EditOrderVO editOrderVO, Errors result)
			throws EntityNotFoundException, ValidationException, EntityExistsException, AlreadyApprovedException {
		ValidationUtil.checkValidationErrors(result);
		orderService.editOrder(currentUser.getId(), editOrderVO);
		return ResponseUtil.buildSuccessResponseEntity("Order edited successfully");
	}
	
	@PutMapping(value = "/approve/{orderId}")
	@Secured({ Constants.ROLE_DISTRIBUTOR })
	public ResponseEntity<String> approveOrder(@PathVariable("orderId") Integer orderId)
			throws EntityNotFoundException, ValidationException, AlreadyApprovedException, EntityExistsException {
		orderService.approveOrder(orderId);
		return ResponseUtil.buildSuccessResponseEntity("Order is approved");
	}
	
	@GetMapping("/get/{orderId}")
	@Secured({ Constants.ROLE_DISTRIBUTOR, Constants.ROLE_WHOLESALER })
	public ResponseEntity<String> getOrder(@PathVariable("orderId") Integer orderId) throws EntityNotFoundException {
		OrderVO orderVO = orderService.getOrderDetailsById(orderId);
		return ResponseUtil.buildSuccessResponseEntity(CommonUtil.convertToJSONString(orderVO));
	}
	
	@GetMapping("/getAll")
	@Secured({ Constants.ROLE_WHOLESALER })
	public ResponseEntity<String> getAllOrders() {
		return ResponseUtil.buildSuccessResponseEntity(CommonUtil.convertToJSONString(orderService.getAllOrders()));
	}
	
	@DeleteMapping("/delete/{orderId}")
	@Secured({ Constants.ROLE_WHOLESALER })
	public ResponseEntity<String> deleteOrder(@PathVariable("orderId") Integer orderId)
			throws EntityNotFoundException, BillAmountNotPaidException {
		orderService.deleteOrderById(orderId);
		return ResponseUtil.buildSuccessResponseEntity("Order deleted successfully");
	}
	
}
