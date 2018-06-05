package com.easybill.exception;

import lombok.Getter;

@Getter
/**
 * Exception class to indicate that bill amount is not paid for an order.
 * 
 * @author abhay.jain
 *
 */
public class BillAmountNotPaidException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public BillAmountNotPaidException(String message) {
		super();
		this.message = message;
	}

}
