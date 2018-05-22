package com.easybill.exception;

import lombok.Getter;

@Getter
/**
 * Exception class to indicate that an entity already exists.
 * 
 * @author abhay.jain
 *
 */
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
