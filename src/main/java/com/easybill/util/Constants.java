package com.easybill.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

public class Constants {

	public enum StatusCode {
		SUCCESS(HttpStatus.OK.value()), FAIL(HttpStatus.BAD_REQUEST.value()), NOT_FOUND(HttpStatus.NOT_FOUND.value());

		private int statusCode;

		private StatusCode(int statusCode) {
			this.statusCode = statusCode;
		}

		public int getStatusCode() {
			return statusCode;
		}

	}

	public static final String EMPTY_STRING = StringUtils.EMPTY;
	public static final String SPACE = StringUtils.SPACE;
	public static final String SEMICOLON = ";";
	public static final String COLON = ":";
	public static final String GENERIC_EXCEPTION_MESSAGE = "Something went wrong, please try after some time.";

}
