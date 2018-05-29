package com.easybill.validation;

public class Patterns {
	public static final String ALPHABETIC_NAME_PATTERN = "[a-zA-Z ]+";

	public static final String EMAIL_PATTERN = "^[A-Za-z]+([._-]{0,1}[A-Za-z0-9]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	/*
	 * (		    # Start of group 
	 * (?=.*\d)     # must contains one digit from 0-9
	 * (?=.*[a-z])  # must contains one lower case character
	 * (?=.*[A-Z])  # must contains one upper case characters 
	 * (?=.*[@#$%]) # must contains one special symbols in the list "!@#$%^&:_"
	 * . 			# match anything with previous condition checking 
	 * {8,50}       # length at least 8 characters and maximum of 50 
	 * ) 			# End of group
	 */
	public static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&:_]).{8,50})";
	
	public static final String ALPHANUMERIC_NAME_PATTERN = "[a-zA-Z0-9 ]+";
	
	public static final String UNIT_PATTERN = "\\bPC\\b|\\bBOX\\b|\\bBAG\\b";

}
