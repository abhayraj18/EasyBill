package com.easybill.exception;

import lombok.Getter;

@Getter
/**
 * Exception class to indicate that an order is already approved.
 * Is thrown when an order is tried to approve again or an edit is made to an already approved order.
 * 
 * @author abhay.jain
 *
 */
public class AlreadyApprovedException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public AlreadyApprovedException(String message) {
		super();
		this.message = message;
	}

}
