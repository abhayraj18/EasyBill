package com.easybill.exception;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.easybill.util.Constants.StatusCode;
import com.easybill.util.ExceptionMessage;
import com.easybill.util.ResponseUtil;

/**
 * Global exception handler class which can handle various types of exceptions and send appropriate
 * error response.
 * 
 * @author abhay.jain
 *
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<String> handleEntityNotFoundException(HttpServletRequest request, Exception ex) {
		return ResponseUtil.buildErrorResponseEntity(ex.getMessage(), StatusCode.NOT_FOUND.getStatus());
	}

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<String> handleValidationException(HttpServletRequest request, Exception ex) {
		return ResponseUtil.buildErrorResponseEntity(ex.getMessage(), StatusCode.FAIL.getStatus());
	}

	@ExceptionHandler(EntityExistsException.class)
	public ResponseEntity<String> handleEntityExistsException(HttpServletRequest request, Exception ex) {
		return ResponseUtil.buildErrorResponseEntity(ex.getMessage(), StatusCode.FAIL.getStatus());
	}

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<String> handleAuthenticationException(HttpServletRequest request, Exception ex) {
		return ResponseUtil.buildErrorResponseEntity(ex.getMessage(), StatusCode.UNAUTHORIZED.getStatus());
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<String> handleAccessDeniedException(HttpServletRequest request, Exception ex) {
		return ResponseUtil.buildErrorResponseEntity(ex.getMessage(), StatusCode.UNAUTHORIZED.getStatus());
	}
	
	@ExceptionHandler(RecentPasswordException.class)
	public ResponseEntity<String> handleRecentPasswordException(HttpServletRequest request, Exception ex) {
		return ResponseUtil.buildErrorResponseEntity(ex.getMessage(), StatusCode.FAIL.getStatus());
	}
	
	@ExceptionHandler(BillAmountNotPaidException.class)
	public ResponseEntity<String> handleBillAmountNotPaidException(HttpServletRequest request, Exception ex) {
		return ResponseUtil.buildErrorResponseEntity(ex.getMessage(), StatusCode.FAIL.getStatus());
	}
	
	@ExceptionHandler(AlreadyApprovedException.class)
	public ResponseEntity<String> handleAlreadyApprovedException(HttpServletRequest request, Exception ex) {
		return ResponseUtil.buildErrorResponseEntity(ex.getMessage(), StatusCode.FAIL.getStatus());
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<String> handleDataIntegrityViolationException(HttpServletRequest request, Exception ex) {
		return ResponseUtil.buildErrorResponseEntity(ex.getMessage(), StatusCode.FAIL.getStatus());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGenericException(HttpServletRequest request, Exception ex) {
		logger.error(ex.getMessage(), ex);
		return ResponseUtil.buildErrorResponseEntity(ExceptionMessage.GENERIC_EXCEPTION_MESSAGE,
				StatusCode.FAIL.getStatus());
	}

}
