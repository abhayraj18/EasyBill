package com.easybill.pojo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.easybill.validation.Patterns;
import com.easybill.validation.ValidationCode;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailForm {

	@NotNull(message = "{" + ValidationCode.EMPTY_EMAIL + "}")
	@Pattern(regexp = Patterns.EMAIL_PATTERN, message = "{" + ValidationCode.INVALID_EMAIL + "}")
	private String email;

}
