package com.easybill.service.impl;

import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easybill.model.Email;
import com.easybill.model.Email.EmailType;
import com.easybill.model.User;
import com.easybill.repository.EmailRepository;
import com.easybill.service.EmailService;
import com.easybill.util.DateUtil;
import com.easybill.util.email.EmailUtil;

@Service
public class EmailServiceImpl implements EmailService {
	
	@Autowired
	private EmailUtil emailUtil;
	
	@Autowired
	private EmailRepository emailRepository;

	@Override
	public void createAndSendEmail(User user, Map<String, Object> data, EmailType emailType) {
		Email email = new Email(user, data, emailType);
		try {
			if (emailUtil.sendEmail(email.getUser().getEmail(), emailType, data)) {
				email.setSent(true);
				email.setSentAt(DateUtil.getCurrentTime());
			}
		} catch (Exception e) {
			e.printStackTrace();
			email.setError(Objects.nonNull(e.getCause()) ? e.getCause().getMessage() : e.getMessage());
		}
		emailRepository.save(email);
	}

}
