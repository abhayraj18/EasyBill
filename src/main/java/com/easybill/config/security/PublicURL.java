package com.easybill.config.security;

/**
 * This class has all the URLs which doesn't require authentication.
 * 
 * @author abhay.jain
 *
 */
public class PublicURL {

	public static final String[] GENERIC_URLS = new String[] {"/auth/**", "/favicon.ico"};
	public static final String[] USER_URLS = new String[] {"/user/isEmailAvailable", "/user/add", "/user/sendResetPasswordEmail", "/user/verifyEmail"};
}
