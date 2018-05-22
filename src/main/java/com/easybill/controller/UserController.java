package com.easybill.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easybill.config.security.CurrentUser;
import com.easybill.config.security.UserPrincipal;
import com.easybill.exception.EntityNotFoundException;
import com.easybill.exception.ValidationException;
import com.easybill.model.User;
import com.easybill.pojo.EditUserForm;
import com.easybill.pojo.ChangePasswordForm;
import com.easybill.pojo.UserVO;
import com.easybill.service.UserService;
import com.easybill.util.CommonUtil;
import com.easybill.util.Constants;
import com.easybill.util.ExceptionMessage;
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

	@GetMapping(value = "/isUsernameAvailable", params = "username")
	public ResponseEntity<String> isUsernameAvailable(String username) throws Exception {
		return ResponseUtil.buildSuccessResponseEntity(userService.isUsernameAvailable(username).toString());
	}
	
	@GetMapping("/get/{userId}")
	@Secured({ Constants.ROLE_DISTRIBUTOR, Constants.ROLE_WHOLESALER })
	public ResponseEntity<String> getUser(@CurrentUser UserPrincipal currentUser, 
			@PathVariable("userId") Integer userId)	throws EntityNotFoundException {
		// Check if authenticated user id matches with passed id
		if (currentUser.getId() != userId) {
			throw new AccessDeniedException(ExceptionMessage.ACCESS_DENIED_MESSAGE);
		}
		UserVO userVO = userService.getUserDetailsById(userId);
		return ResponseUtil.buildSuccessResponseEntity(CommonUtil.convertToJSONString(userVO));
	}

	@PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addUser(@RequestBody @Validated UserVO userVO, Errors result) throws Exception {
		// Do custom validations
		validator.validate(userVO, result);
		if (result.hasErrors()) {
			Map<String, List<String>> errorMap = MessageUtil.getErrorMessages(result);
			throw new ValidationException(CommonUtil.convertToJSONString(errorMap));
		}

		User user = userService.addUser(userVO);
		userVO.setId(user.getId());
		userVO.setPassword(user.getPassword());
		return ResponseUtil.buildSuccessResponseEntity(CommonUtil.convertToJSONString(userVO));
	}

	@PostMapping(value = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
	@Secured({ Constants.ROLE_DISTRIBUTOR, Constants.ROLE_WHOLESALER })
	public ResponseEntity<String> editUser(@CurrentUser UserPrincipal currentUser, 
			@RequestBody @Validated EditUserForm userForm, Errors result) throws Exception {
		if (result.hasErrors()) {
			Map<String, List<String>> errorMap = MessageUtil.getErrorMessages(result);
			throw new ValidationException(CommonUtil.convertToJSONString(errorMap));
		}
		
		if (currentUser.getId() != userForm.getId()) {
			throw new AccessDeniedException(ExceptionMessage.UNAUTHORIZED_OPERATION_MESSAGE);
		}
		
		userService.editUser(userForm);
		return ResponseUtil.buildSuccessResponseEntity("User is edited successfully");
	}
	
	@PutMapping(value = "/inactivate/{userId}")
	@Secured({ Constants.ROLE_DISTRIBUTOR, Constants.ROLE_WHOLESALER })
	public ResponseEntity<String> inactivateUser(@CurrentUser UserPrincipal currentUser, 
			@PathVariable("userId") Integer userId) throws Exception {
		// Check if authenticated user id matches with passed id
		if (currentUser.getId() != userId) {
			throw new AccessDeniedException(ExceptionMessage.UNAUTHORIZED_OPERATION_MESSAGE);
		}
		
		userService.inactivateUser(userId);
		return ResponseUtil.buildSuccessResponseEntity("User is inactivated successfully");
	}
	
	@PostMapping(value = "/changePassword", consumes = MediaType.APPLICATION_JSON_VALUE)
	@Secured({ Constants.ROLE_DISTRIBUTOR, Constants.ROLE_WHOLESALER })
	public ResponseEntity<String> changePassword(@CurrentUser UserPrincipal currentUser, 
			@RequestBody @Validated ChangePasswordForm changePasswordForm, Errors result) throws Exception {
		if (result.hasErrors()) {
			Map<String, List<String>> errorMap = MessageUtil.getErrorMessages(result);
			throw new ValidationException(CommonUtil.convertToJSONString(errorMap));
		}
		
		if (currentUser.getId() != changePasswordForm.getId()) {
			throw new AccessDeniedException(ExceptionMessage.UNAUTHORIZED_OPERATION_MESSAGE);
		}
		
		userService.changePassword(changePasswordForm);
		return ResponseUtil.buildSuccessResponseEntity("Password changed successfully");
	}

}
