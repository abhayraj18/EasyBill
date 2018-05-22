package com.easybill.exception;

import lombok.Getter;

@Getter
/**
 * Exception class to indicate that a password was used recently.
 * 
 * @author abhay.jain
 *
 */
public class RecentPasswordException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public RecentPasswordException(String message) {
		super();
		this.message = message;
	}

}
