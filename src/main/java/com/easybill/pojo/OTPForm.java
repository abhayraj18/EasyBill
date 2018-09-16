package com.easybill.pojo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.easybill.validation.Patterns;
import com.easybill.validation.ValidationCode;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OTPForm extends EmailForm {

	@NotNull(message = "{" + ValidationCode.EMPTY_OTP + "}")
	@Pattern(regexp = Patterns.NUMERIC_PATTERN, message = "{" + ValidationCode.INVALID_OTP + "}")
	private Integer otp;

}
