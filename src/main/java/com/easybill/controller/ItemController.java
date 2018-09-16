package com.easybill.controller;

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
import com.easybill.pojo.ItemVO;
import com.easybill.service.ItemService;
import com.easybill.util.CommonUtil;
import com.easybill.util.Constants;
import com.easybill.util.ResponseUtil;
import com.easybill.validation.PojoValidator;
import com.easybill.validation.ValidationCode;
import com.easybill.validation.ValidationUtil;

@RestController
@RequestMapping(value = "/item")
public class ItemController {

	@Autowired
	private PojoValidator validator;
	
	@Autowired
	private ItemService itemService;

	@PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
	@Secured(Constants.ROLE_DISTRIBUTOR)
	public ResponseEntity<String> addItem(@CurrentUser UserPrincipal currentUser, @RequestBody @Validated ItemVO itemVO,
			Errors result) throws EntityNotFoundException, ValidationException, EntityExistsException {
		// Do custom validations
		validator.validate(itemVO, result);
		ValidationUtil.checkValidationErrors(result);
		itemService.addItem(currentUser.getId(), itemVO);
		return ResponseUtil.buildSuccessResponseEntity("Item added successfully");
	}
	
	@PostMapping(value = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
	@Secured(Constants.ROLE_DISTRIBUTOR)
	public ResponseEntity<String> editItem(@CurrentUser UserPrincipal currentUser, @RequestBody @Validated ItemVO itemVO,
			Errors result) throws EntityNotFoundException, ValidationException, EntityExistsException {
		// Do custom validations
		if (!CommonUtil.isValidId(itemVO.getId())) {
			result.reject(ValidationCode.INVALID_ID);
		}
		validator.validate(itemVO, result);
		ValidationUtil.checkValidationErrors(result);
		itemService.editItem(currentUser.getId(), itemVO);
		return ResponseUtil.buildSuccessResponseEntity("Item added successfully");
	}

	@GetMapping("/get/{itemId}")
	@Secured(Constants.ROLE_DISTRIBUTOR)
	public ResponseEntity<String> getItem(@PathVariable("itemId") Integer itemId) throws EntityNotFoundException {
		ItemVO itemVO = itemService.getItemDetailsById(itemId);
		return ResponseUtil.buildSuccessResponseEntity(CommonUtil.convertToJSONString(itemVO));
	}
	
	@GetMapping("/getAll")
	@Secured({ Constants.ROLE_DISTRIBUTOR, Constants.ROLE_WHOLESALER })
	public ResponseEntity<String> getAllItems() {
		return ResponseUtil.buildSuccessResponseEntity(CommonUtil.convertToJSONString(itemService.getAllItems()));
	}
	
}
