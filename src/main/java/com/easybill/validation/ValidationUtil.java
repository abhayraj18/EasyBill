package com.easybill.validation;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.easybill.exception.ValidationException;
import com.easybill.util.CommonUtil;

public class ValidationUtil {

	/**
	 * Get error messages for each field in a map from Errors
	 * 
	 * @param result
	 * @return
	 */
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

	/**
	 * Get Global error messages from validation errors.
	 * 
	 * @param errorMap
	 * @param error
	 */
	private static void getGlobalErrors(Map<String, List<String>> errorMap, ObjectError error) {
		List<String> fieldErrors = errorMap.get(error.getCode()) == null ? new ArrayList<>()
				: errorMap.get(error.getCode());
		String message = ValidationMessage.get(error.getCode()) != null ? ValidationMessage.get(error.getCode())
				: error.getDefaultMessage();
		// Format the message using the arguments passed
		message = MessageFormat.format(message, error.getArguments());
		fieldErrors.add(message);
		errorMap.put(error.getCode(), fieldErrors);
	}

	/**
	 * Get Field error messages from validation errors.
	 * 
	 * @param errorMap
	 * @param error
	 */
	private static void getFieldErrors(Map<String, List<String>> errorMap, ObjectError error) {
		FieldError fieldError = (FieldError) error;
		List<String> fieldErrors = errorMap.get(fieldError.getField()) == null ? new ArrayList<>()
				: errorMap.get(fieldError.getField());
		fieldErrors.add(error.getDefaultMessage());
		errorMap.put(fieldError.getField(), fieldErrors);
	}
	
	/**
	 * Throw @ValidationException if there are any validation errors.
	 * 
	 * @param result
	 * @throws ValidationException
	 */
	public static void checkValidationErrors(Errors result) throws ValidationException {
		if (result.hasErrors()) {
			Map<String, List<String>> errorMap = ValidationUtil.getErrorMessages(result);
			throw new ValidationException(CommonUtil.convertToJSONString(errorMap));
		}
	}

}
