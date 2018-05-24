package com.easybill.util.email;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class EmailUtil {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private JavaMailSender sender;
	
	@Autowired
    private Configuration freemarkerConfig;

	public boolean sendEmail(Email email) throws Exception {
        try {
			MimeMessage message = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);

			// set loading location to src/main/resources/emailTemplates
			freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/emailTemplates");
			
			Template template = freemarkerConfig.getTemplate(email.getEmailType().getTemplate());
			String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, email.getData());
			helper.setTo(email.getRecipient());
			helper.setSubject(email.getEmailType().getSubject());
			helper.setText(text, true);
			
			sender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("Could not send email: " + email.getEmailType());
			throw e;
		}
		return Boolean.TRUE;
    }
	    
}
