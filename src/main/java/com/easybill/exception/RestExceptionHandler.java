package com.easybill.exception;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.easybill.util.Constants;
import com.easybill.util.Constants.StatusCode;
import com.easybill.util.ResponseUtil;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<String> handleEntityNotFoundException(HttpServletRequest request, Exception ex) {
		return ResponseUtil.buildErrorResponseEntity(ex.getMessage(), StatusCode.NOT_FOUND.getStatusCode());
	}

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<String> handleValidationException(HttpServletRequest request, Exception ex) {
		return ResponseUtil.buildErrorResponseEntity(ex.getMessage(), StatusCode.FAIL.getStatusCode());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGenericException(HttpServletRequest request, Exception ex) {
		logger.error(ex.getMessage(), ex);
		return ResponseUtil.buildErrorResponseEntity(Constants.GENERIC_EXCEPTION_MESSAGE,
				StatusCode.FAIL.getStatusCode());
	}

}
