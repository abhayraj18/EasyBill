package com.easybill.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Objects;
import java.util.regex.Pattern;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.easybill.validation.Patterns;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class CommonUtil {

	private final static Logger logger = LoggerFactory.getLogger(CommonUtil.class);

	@Value("${server.url}")
	@Getter
	private String serverUrl;

	@Value("${randomToken.length}")
	@Getter
	private String randomTokenLength;

	private final int RANDOM_TOKEN_LENGTH = 16;

	public static String convertToJSONString(Object object) {
		if (object instanceof String) {
			return object.toString();
		}
		String result = StringUtils.EMPTY;
		try {
			result = new ObjectMapper().writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static byte[] serialize(Object obj) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(out);
			os.writeObject(obj);
			return out.toByteArray();
		} catch (IOException e) {
			logger.error("Error while serializing data");
			e.printStackTrace();
			return new byte[] {};
		}
	}

	public static Object deserialize(byte[] data) {
		try {
			ByteArrayInputStream in = new ByteArrayInputStream(data);
			ObjectInputStream is = new ObjectInputStream(in);
			return is.readObject();
		} catch (ClassNotFoundException e) {
			logger.error("ClassNotFoundException, error while de-serializing data");
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("IOException, error while de-serializing data");
			e.printStackTrace();
		}
		return null;
	}

	public String getRandomAlphaNumericToken() {
		return RandomStringUtils.randomAlphanumeric(StringUtils.isNotBlank(getRandomTokenLength())
				? Integer.parseInt(getRandomTokenLength().trim()) : RANDOM_TOKEN_LENGTH);
	}

	public static boolean isValidId(Integer id) {
		return Objects.nonNull(id) && id > 0;
	}
	
	public static boolean isValidUnit(String unit) {
		Pattern unitPattern = Pattern.compile(Patterns.UNIT_PATTERN);
		if (!unitPattern.matcher(unit).find()) {
			return false;
		}
		return true;
	}

}
