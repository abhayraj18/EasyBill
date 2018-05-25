package com.easybill.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
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

import org.slf4j.LoggerFactory;

import com.easybill.util.DateUtil;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Email {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(columnDefinition = "text")
	private byte[] data;
	
	@Enumerated(EnumType.STRING)
	private EmailType emailType;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	@Column(columnDefinition = "bit(1) default b'0'")
	private boolean sent;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date sentAt;
	
	@Column(length = 500)
	private String error;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", nullable = false)
	private User user;
	
	public Email(User user, Map<String, Object> data, EmailType emailType) {
		this.user = user;
		this.data = getDataInBytes(data);
		this.emailType = emailType;
		setCreatedAt(DateUtil.getCurrentTime());
	}

	private byte[] getDataInBytes(Map<String, Object> data) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = null;
		try {
			out = new ObjectOutputStream(bos);
			out.writeObject(data);
			out.flush();
			return bos.toByteArray();
		} catch (IOException e) {
			LoggerFactory.getLogger(this.getClass()).error("Error while serializing data");
			e.printStackTrace();
		} finally {
			try {
				bos.close();
			} catch (IOException ex) {
				LoggerFactory.getLogger(this.getClass()).error("Error while closing BOS");
				ex.printStackTrace();
			}
		}
		return new byte[]{};
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
