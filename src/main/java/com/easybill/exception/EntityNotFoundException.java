package com.easybill.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public EntityNotFoundException(String message) {
		super();
		this.message = message;
	}

}
