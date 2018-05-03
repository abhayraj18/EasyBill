package com.easybill.util;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

public class MessageUtil {

	public static Map<String, List<String>> getErrorMessages(Errors result) {
		Map<String, List<String>> errorMap = new HashMap<>();
		result.getAllErrors().forEach(error -> {
			if (error instanceof FieldError) {
				getFieldErrors(errorMap, error);
			} else {
				getGlobalErrors(errorMap, error);
			}
		});
		return errorMap;
	}

	private static void getGlobalErrors(Map<String, List<String>> errorMap, ObjectError error) {
		List<String> fieldErrors = errorMap.get(error.getCode()) == null ? new ArrayList<>()
				: errorMap.get(error.getCode());
		String message = Messages.get(error.getCode()) != null ? Messages.get(error.getCode())
				: error.getDefaultMessage();
		message = MessageFormat.format(message, error.getArguments());
		fieldErrors.add(message);
		errorMap.put(error.getCode(), fieldErrors);
	}

	private static void getFieldErrors(Map<String, List<String>> errorMap, ObjectError error) {
		FieldError fieldError = (FieldError) error;
		List<String> fieldErrors = errorMap.get(fieldError.getField()) == null ? new ArrayList<>()
				: errorMap.get(fieldError.getField());
		fieldErrors.add(error.getDefaultMessage());
		errorMap.put(fieldError.getField(), fieldErrors);
	}

}
