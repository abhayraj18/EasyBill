package com.easybill.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

public class Constants {

	public static final String EMPTY_STRING = StringUtils.EMPTY;
	public static final String SPACE = StringUtils.SPACE;
	public static final HttpStatus SUCCESS = HttpStatus.OK;
	public static final HttpStatus FAIL = HttpStatus.BAD_REQUEST;
	public static final String SEMICOLON = ";";
	public static final String COLON = ":";
	public static final String GENERIC_EXCEPTION_MESSAGE = "Something went wrong, please try after some time.";

}
