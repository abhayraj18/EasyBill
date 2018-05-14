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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easybill.exception.EntityExistsException;
import com.easybill.exception.ValidationException;
import com.easybill.model.User;
import com.easybill.pojo.UserVO;
import com.easybill.service.UserService;
import com.easybill.util.CommonUtil;
import com.easybill.util.Constants;
import com.easybill.util.MessageUtil;
import com.easybill.util.ResponseUtil;
import com.easybill.validation.PojoValidator;

@RestController
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private PojoValidator validator;

	@GetMapping(value = "/isEmailAvailable", params = "email")
	public ResponseEntity<String> isEmailAvailable(String email) throws Exception {
		return ResponseUtil.buildSuccessResponseEntity(userService.isEmailAvailable(email).toString());
	}

	@PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addUser(@RequestBody @Validated UserVO userVO, Errors result) throws Exception {
		validator.validate(userVO, result);
		if (result.hasErrors()) {
			Map<String, List<String>> errorMap = MessageUtil.getErrorMessages(result);
			throw new ValidationException(CommonUtil.convertToJSONString(errorMap));
		}

		if (!userService.isEmailAvailable(userVO.getEmail())) {
			throw new EntityExistsException("User already exists with email: " + userVO.getEmail());
		}

		User user = userService.saveUser(userVO);
		userVO.setId(user.getId());
		userVO.setPassword(user.getPassword());
		return ResponseUtil.buildSuccessResponseEntity(CommonUtil.convertToJSONString(userVO));
	}

	@PostMapping(value = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
	@Secured({ Constants.ROLE_DISTRIBUTOR, Constants.ROLE_WHOLESALER })
	public ResponseEntity<String> editUser(@RequestBody @Validated UserVO userVO, Errors result) throws Exception {
		if (result.hasErrors()) {
			Map<String, List<String>> errorMap = MessageUtil.getErrorMessages(result);
			throw new ValidationException(CommonUtil.convertToJSONString(errorMap));
		}

		if (!userService.isEmailAvailable(userVO.getEmail())) {
			throw new EntityExistsException("User already exists with email: " + userVO.getEmail());
		}

		return ResponseUtil.buildSuccessResponseEntity(CommonUtil.convertToJSONString(userVO));
	}

}
