package com.easybill.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ResponseUtil {

	public static String convertToJSONString(Object object) {
		String result = StringUtils.EMPTY;
		try {
			result = new ObjectMapper().writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Response buildSuccessResponseEntity(Object response, int statusCode) {
		return new Response(convertToJSONString(response), statusCode);
	}

	public static ResponseEntity<String> buildErrorResponseEntity(String message, int statusCode) {
		return new ResponseEntity<String>(message, HttpStatus.valueOf(statusCode));
	}

}
