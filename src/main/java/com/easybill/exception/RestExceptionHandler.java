package com.easybill.exception;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.easybill.util.Constants;
import com.easybill.util.ResponseUtil;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@ExceptionHandler(EntityNotFoundException.class)

	public ResponseEntity<Object> handleEntityNotFoundException(HttpServletRequest request, Exception ex) {
		return ResponseUtil.buildResponseEntity(ex.getMessage(), Constants.FAIL);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleGenericException(HttpServletRequest request, Exception ex) {
		logger.error(ex.getMessage(), ex);
		return ResponseUtil.buildResponseEntity(Constants.GENERIC_EXCEPTION_MESSAGE, Constants.FAIL);
	}

}
