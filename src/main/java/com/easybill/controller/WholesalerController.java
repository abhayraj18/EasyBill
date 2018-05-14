package com.easybill.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easybill.exception.EntityNotFoundException;
import com.easybill.pojo.UserVO;
import com.easybill.service.UserService;
import com.easybill.util.CommonUtil;
import com.easybill.util.Constants;
import com.easybill.util.ResponseUtil;

@RestController
@RequestMapping(value = "/wholesaler")
@Secured(Constants.ROLE_WHOLESALER)
public class WholesalerController {

	@Autowired
	private UserService userService;

	@GetMapping("/get/{wholesalerId}")
	public ResponseEntity<String> getWholesaler(@PathVariable("wholesalerId") Integer wholesalerId)
			throws EntityNotFoundException {
		UserVO userVO = userService.getUserById(wholesalerId);
		return ResponseUtil.buildSuccessResponseEntity(CommonUtil.convertToJSONString(userVO));
	}

}
