package com.easybill.util.email;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Email {

	private String recipient;
	private Map<String, Object> data;
	private EmailType emailType;
	
	public enum EmailType {
		RESET_PASSWORD("Reset Password Request", "reset_password.ftl");
		
		@Getter
		private String subject;
		@Getter
		private String template;

		private EmailType(String subject, String template) {
			this.subject = subject;
			this.template = template;
		}
	}

}
