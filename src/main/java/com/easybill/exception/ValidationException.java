package com.easybill.exception;

import lombok.Getter;

/**
 * Exception class to indicate there are validation errors.
 * 
 * @author abhay.jain
 *
 */
@Getter
public class ValidationException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public ValidationException(String message) {
		super();
		this.message = message;
	}

}