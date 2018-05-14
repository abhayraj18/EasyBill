package com.easybill.exception;

import lombok.Getter;

@Getter
public class EntityExistsException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public EntityExistsException(String message) {
		super();
		this.message = message;
	}

}
