package com.easybill.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.easybill.util.Constants.StatusCode;

public class ResponseUtil {

	public static ResponseEntity<String> buildSuccessResponseEntity(String message) {
		return new ResponseEntity<String>(message, StatusCode.SUCCESS.getStatus());
	}

	public static ResponseEntity<String> buildErrorResponseEntity(String message, HttpStatus httpStatus) {
		return new ResponseEntity<String>(message, httpStatus);
	}

}
