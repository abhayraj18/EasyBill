package com.easybill.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easybill.config.security.JwtAuthenticationResponse;
import com.easybill.config.security.JwtTokenProvider;
import com.easybill.config.security.UserPrincipal;
import com.easybill.exception.ValidationException;
import com.easybill.pojo.LoginRequest;
import com.easybill.util.CommonUtil;
import com.easybill.util.Constants.StatusCode;
import com.easybill.util.ResponseUtil;
import com.easybill.validation.ValidationUtil;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@PostMapping("/login")
	public ResponseEntity<String> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, Errors result)
			throws AuthenticationException, ValidationException {
		ValidationUtil.checkValidationErrors(result);
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		if (!((UserPrincipal) authentication.getDetails()).isEmailVerified()) {
			return ResponseUtil.buildErrorResponseEntity("Please verify email to login", StatusCode.FAIL.getStatus());
		}
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = tokenProvider.generateToken(authentication);
		return ResponseUtil
				.buildSuccessResponseEntity(CommonUtil.convertToJSONString(new JwtAuthenticationResponse(jwt)));
	}

}
