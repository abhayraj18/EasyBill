package com.easybill.util;

import java.text.DateFormat;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {
	
	private final static Logger logger = LoggerFactory.getLogger(DateUtil.class);

	public static final String YYYY_MM_DD_HH_MM_SS_A_FORMAT = "yyyy-MM-dd hh:mm:ss a";
	public static final String DD_MM_YYYY_HH_MM_SS_A_FORMAT = "dd-MM-yyyy hh:mm:ss a";
	public static final String DD_MMM_YYYY_HH_MM_SS_A_FORMAT = "dd MMM, yyyy hh:mm:ss a";
	
	public static Date getCurrentTime() {
		return new Date();
	}

	public static String formatDateToString(Date date, String pattern) {
		try {
			return DateFormatUtils.format(date, pattern);
		} catch (Exception e) {
			logger.error("Error while formating date: " + date);
			e.printStackTrace();
		}
		return Constants.EMPTY_STRING;
	}
	
	/**
	 * Return date in MMM d, yyyy h:mm:ss a format
	 * @param date
	 * @return
	 */
	public static String formatDateToDateTimeInstance(Date date) {
		try {
			return DateFormat.getDateTimeInstance().format(date);
		} catch (Exception e) {
			logger.error("Error while formating date: " + date);
			e.printStackTrace();
		}
		return Constants.EMPTY_STRING;
	}
	
	public static Date parseStringToDate(String dateString, String pattern) {
		try {
			FastDateFormat format = FastDateFormat.getInstance(pattern);
			return format.parse(dateString);
		} catch (Exception e) {
			logger.error("Error while parsing date string: " + dateString + ", pattern: " + pattern);
			e.printStackTrace();
		}
		return null;
	}

}
