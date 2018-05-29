package com.easybill.util.otp;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import com.easybill.util.DateUtil;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

@Service
public class OTPGenerator {

	private static final Integer EXPIRE_MIN = 5;
	private LoadingCache<String, Integer> otpCache;

	/**
	 * Constructor configuration.
	 */
	public OTPGenerator() {
		super();
		otpCache = CacheBuilder.newBuilder().expireAfterWrite(EXPIRE_MIN, TimeUnit.MINUTES)
				.build(new CacheLoader<String, Integer>() {
					@Override
					public Integer load(String s) throws Exception {
						return 0;
					}
				});
	}

	/**
	 * Method for generating OTP and putting it in cache.
	 *
	 * @param key
	 * @return Map<Integer, Date> having OTP as key and date till when its valid as value 
	 */
	public Map<Integer, Date> generateOTP(String key) {
		Map<Integer, Date> otpMap = new HashMap<>();
		Random random = new Random();
		int OTP = 100000 + random.nextInt(900000);
		otpCache.put(key, OTP);
		otpMap.put(OTP, DateUtils.addMinutes(DateUtil.getCurrentTime(), EXPIRE_MIN));
		return otpMap;
	}

	/**
	 * Method for getting OTP value by key.
	 *
	 * @param key
	 * @return OTP value
	 */
	public Integer getOTPByKey(String key) {
		try {
			return otpCache.get(key);
		} catch (ExecutionException e) {
			return -1;
		}
	}

	/**
	 * Method for removing key from cache.
	 *
	 * @param key
	 */
	public void clearOTPFromCache(String key) {
		otpCache.invalidate(key);
	}
}
