package com.easybill.model;

import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.easybill.util.CommonUtil;
import com.easybill.util.DateUtil;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Email {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(columnDefinition = "text", updatable = false)
	private byte[] data;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, updatable = false)
	private EmailType emailType;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	private Date createdAt;
	
	@Column(columnDefinition = "bit(1) default b'0'")
	private boolean sent;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false)
	private Date sentAt;
	
	@Column(length = 500)
	private String error;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", nullable = false)
	private User user;
	
	public Email(User user, Map<String, Object> data, EmailType emailType) {
		this.user = user;
		this.data = CommonUtil.serialize(data);
		this.emailType = emailType;
		setCreatedAt(DateUtil.getCurrentTime());
	}

	public enum EmailType {
		VERIFY_EMAIL("Email verification", "verify_email.ftl"),
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
