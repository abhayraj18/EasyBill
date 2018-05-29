package com.easybill.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easybill.config.security.CurrentUser;
import com.easybill.config.security.UserPrincipal;
import com.easybill.exception.EntityNotFoundException;
import com.easybill.exception.ValidationException;
import com.easybill.pojo.ItemVO;
import com.easybill.util.CommonUtil;
import com.easybill.util.Constants;
import com.easybill.util.ResponseUtil;
import com.easybill.util.ValidationUtil;
import com.easybill.validation.PojoValidator;

@RestController
@RequestMapping(value = "/item")
@Secured(Constants.ROLE_DISTRIBUTOR)
public class ItemController {

	@Autowired
	private PojoValidator validator;

	@GetMapping("/add")
	public ResponseEntity<String> addItem(@CurrentUser UserPrincipal currentUser,
			@RequestBody @Validated ItemVO itemVO, Errors result) throws EntityNotFoundException, ValidationException {
		// Do custom validations
		validator.validate(itemVO, result);
		if (result.hasErrors()) {
			Map<String, List<String>> errorMap = ValidationUtil.getErrorMessages(result);
			throw new ValidationException(CommonUtil.convertToJSONString(errorMap));
		}
		return ResponseUtil.buildSuccessResponseEntity("Item added successfully");
	}

}
