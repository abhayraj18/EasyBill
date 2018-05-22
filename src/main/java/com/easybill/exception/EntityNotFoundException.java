package com.easybill.exception;

import lombok.Getter;

/**
 * Exception class to indicate that an entity does not exist.
 * 
 * @author abhay.jain
 *
 */
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
