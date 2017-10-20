package com.bill.management.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

public class Constants {

	public static final String EMPTY_STRING = StringUtils.EMPTY;
	public static final HttpStatus SUCCESS = HttpStatus.OK;
	public static final HttpStatus FAIL = HttpStatus.BAD_REQUEST;
	public static final String SEMICOLON = ";";
		
}
