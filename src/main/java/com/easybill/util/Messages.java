package com.easybill.util;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class Messages {

	static Properties props = null;
	static {
		Resource resource = new ClassPathResource("/messages.properties");
		try {
			props = PropertiesLoaderUtils.loadProperties(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String get(String code) {
		return props.getProperty(code);
	}

}
