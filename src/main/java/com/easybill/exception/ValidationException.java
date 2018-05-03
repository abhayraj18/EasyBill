package com.easybill.exception;

import lombok.Getter;

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