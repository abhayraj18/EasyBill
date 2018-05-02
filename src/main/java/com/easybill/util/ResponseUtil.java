package com.easybill.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ResponseUtil {

	public static String convertPOJOToString(Object object) {
		String result = StringUtils.EMPTY;
		try {
			result = new ObjectMapper().writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Response getResponse(String response, int statusCode) {
		return new Response(response, statusCode);
	}

	public static ResponseEntity<Object> buildResponseEntity(Object response, HttpStatus status) {
		return new ResponseEntity<Object>(response, status);
	}

}
