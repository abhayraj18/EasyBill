package com.easybill.util;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class ValidationMessage {

	private static final Logger logger = LoggerFactory.getLogger(ValidationMessage.class);
	static Properties props = null;
	static {
		Resource resource = new ClassPathResource("/validation_messages.properties");
		try {
			props = PropertiesLoaderUtils.loadProperties(resource);
		} catch (IOException e) {
			logger.error("Could not load validation properties");
			e.printStackTrace();
		}
	}

	public static String get(String code) {
		return props.getProperty(code);
	}

}
