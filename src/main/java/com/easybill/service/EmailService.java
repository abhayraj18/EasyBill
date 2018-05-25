package com.easybill.service;

import java.util.Map;

import com.easybill.model.Email.EmailType;
import com.easybill.model.User;

public interface EmailService {

	void createAndSendEmail(User user, Map<String, Object> data, EmailType emailType);

}
