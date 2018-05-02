package com.easybill.config.application;

import org.springframework.stereotype.Component;
import org.springframework.validation.MessageCodesResolver;

@Component
public class CustomMessageCodesResolver implements MessageCodesResolver {

	@Override
	public String[] resolveMessageCodes(String errorCode, String objectName) {
		System.out.println(errorCode + objectName);

		String customErrorCode = "mobile/" + objectName + "/" + errorCode;
		return new String[] { customErrorCode };
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String[] resolveMessageCodes(String errorCode, String objectName, String field, Class fieldType) {

		String customErrorCode = "mobile/" + objectName + "/" + fieldType.getName() + "/" + field + "/" + errorCode;
		return new String[] { customErrorCode };
	}
}