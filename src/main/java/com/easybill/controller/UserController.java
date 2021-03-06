package com.easybill.controller;

import org.apache.commons.lang3.StringUtils;
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
import com.easybill.exception.EntityExistsException;
import com.easybill.exception.EntityNotFoundException;
import com.easybill.exception.RecentPasswordException;
import com.easybill.exception.ValidationException;
import com.easybill.model.User;
import com.easybill.pojo.ChangePasswordForm;
import com.easybill.pojo.EditUserForm;
import com.easybill.pojo.EmailForm;
import com.easybill.pojo.ResetPasswordForm;
import com.easybill.pojo.UserVO;
import com.easybill.pojo.OTPForm;
import com.easybill.service.UserService;
import com.easybill.util.CommonUtil;
import com.easybill.util.Constants;
import com.easybill.util.Constants.StatusCode;
import com.easybill.util.ExceptionMessage;
import com.easybill.util.ResponseUtil;
import com.easybill.validation.PojoValidator;
import com.easybill.validation.ValidationUtil;

@RestController
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private PojoValidator validator;
	
	@GetMapping(value = "/isUsernameAvailable", params = "username")
	public ResponseEntity<String> isUsernameAvailable(String username) throws EntityExistsException {
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
	public ResponseEntity<String> addUser(@RequestBody @Validated UserVO userVO, Errors result)
			throws ValidationException, EntityExistsException, EntityNotFoundException {
		// Do custom validations
		validator.validate(userVO, result);
		ValidationUtil.checkValidationErrors(result);

		User user = userService.addUser(userVO);
		userVO.setId(user.getId());
		userVO.setPassword(user.getPassword());
		return ResponseUtil.buildSuccessResponseEntity(CommonUtil.convertToJSONString(userVO));
	}

	@PostMapping(value = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
	@Secured({ Constants.ROLE_DISTRIBUTOR, Constants.ROLE_WHOLESALER })
	public ResponseEntity<String> editUser(@CurrentUser UserPrincipal currentUser, 
			@RequestBody @Validated EditUserForm userForm, Errors result)
			throws ValidationException, EntityNotFoundException, EntityExistsException {
		ValidationUtil.checkValidationErrors(result);

		if (currentUser.getId() != userForm.getId()) {
			throw new AccessDeniedException(ExceptionMessage.UNAUTHORIZED_OPERATION_MESSAGE);
		}

		userService.editUser(userForm);
		return ResponseUtil.buildSuccessResponseEntity("User is edited successfully");
	}
	
	@GetMapping(value = "/verifyEmail", params = {"id", "token"})
	public ResponseEntity<String> verifyEmail(String id, String token)
			throws NumberFormatException, EntityNotFoundException {
		if (StringUtils.isBlank(id) || StringUtils.isBlank(token)) {
			return ResponseUtil.buildErrorResponseEntity("Email could not be verified", StatusCode.FAIL.getStatus());
		}

		if (userService.verifyEmail(id, token)) {
			return ResponseUtil.buildSuccessResponseEntity("Email verified successfully");
		} else {
			return ResponseUtil.buildErrorResponseEntity("Email could not be verified", StatusCode.FAIL.getStatus());
		}
	}
	
	@PutMapping(value = "/inactivate/{userId}")
	@Secured({ Constants.ROLE_DISTRIBUTOR, Constants.ROLE_WHOLESALER })
	public ResponseEntity<String> inactivateUser(@CurrentUser UserPrincipal currentUser, 
			@PathVariable("userId") Integer userId) throws EntityNotFoundException {
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
			@RequestBody @Validated ChangePasswordForm changePasswordForm, Errors result)
			throws ValidationException, EntityNotFoundException, RecentPasswordException {
		ValidationUtil.checkValidationErrors(result);

		if (currentUser.getId() != changePasswordForm.getId()) {
			throw new AccessDeniedException(ExceptionMessage.UNAUTHORIZED_OPERATION_MESSAGE);
		}

		userService.changePassword(changePasswordForm);
		return ResponseUtil.buildSuccessResponseEntity("Password changed successfully");
	}
	
	@PostMapping(value = "/sendResetPasswordEmail")
	public ResponseEntity<String> sendResetPasswordEmail(@RequestBody @Validated EmailForm emailForm)
			throws EntityNotFoundException {
		userService.sendResetPasswordEmail(emailForm.getEmail());
		return ResponseUtil.buildSuccessResponseEntity("Password reset email sent successfully");
	}
	
	@PostMapping(value = "/validateOTP")
	public ResponseEntity<String> validateOTP(@RequestBody @Validated OTPForm otpForm,
			Errors result) throws ValidationException {
		ValidationUtil.checkValidationErrors(result);
		if (userService.validateOTP(otpForm)) {
			return ResponseUtil.buildSuccessResponseEntity("OTP is valid");
		} else {
			return ResponseUtil.buildErrorResponseEntity("OTP is invalid", StatusCode.FAIL.getStatus());
		}
	}
	
	@PostMapping(value = "/resetPassword")
	public ResponseEntity<String> resetPassword(@RequestBody @Validated ResetPasswordForm resetPasswordForm,
			Errors result) throws ValidationException, EntityNotFoundException, RecentPasswordException  {
		ValidationUtil.checkValidationErrors(result);

		userService.resetPassword(resetPasswordForm);
		return ResponseUtil.buildSuccessResponseEntity("Password reset successfully");
	}

}
