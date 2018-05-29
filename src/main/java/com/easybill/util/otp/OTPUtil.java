package com.easybill.util.otp;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OTPUtil {

	@Autowired
	private OTPGenerator otpGenerator;

	/**
	 * Method to get OTP
	 *  
	 * @param key
	 * @return generated OTP
	 */
	public Map<Integer, Date> getOTP(String key) {
		return otpGenerator.generateOTP(key);
	}

	/**
	 * Method to validated passed OTP using key
	 * 
	 * @param key
	 * @param otpNumber
	 * @return true if OTP is valid, else false
	 */
	public Boolean validateOTP(String key, Integer otpNumber) {
		Integer cacheOTP = otpGenerator.getOTPByKey(key);
		if (cacheOTP.equals(otpNumber)) {
			otpGenerator.clearOTPFromCache(key);
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
}