package com.easybill.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

public class Constants {

	public enum StatusCode {
		SUCCESS(HttpStatus.OK), FAIL(HttpStatus.BAD_REQUEST), NOT_FOUND(HttpStatus.NOT_FOUND),
		UNAUTHORIZED(HttpStatus.UNAUTHORIZED);

		private HttpStatus status;

		private StatusCode(HttpStatus status) {
			this.status = status;
		}

		public HttpStatus getStatus() {
			return status;
		}

	}

	public static final String EMPTY_STRING = StringUtils.EMPTY;
	public static final String SPACE = StringUtils.SPACE;
	public static final String SEMICOLON = ";";
	public static final String COLON = ":";
	public static final String UNDERSCORE = "_";

	public static final String ROLE_DISTRIBUTOR = "ROLE_DISTRIBUTOR";
	public static final String ROLE_WHOLESALER = "ROLE_WHOLESALER";
	public static final String PIPE = "|";
	
}
