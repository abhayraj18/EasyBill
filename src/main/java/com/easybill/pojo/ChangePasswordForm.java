package com.easybill.pojo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.easybill.validation.Patterns;
import com.easybill.validation.ValidationCode;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordForm {

	@NotNull(message = "{" + ValidationCode.INVALID_ID + "}")
	@Min(value = 1, message = "{" + ValidationCode.INVALID_ID + "}")
	private Integer id;

	@NotNull(message = "Please enter current password")
	private String currentPassword;
	
	@NotNull(message = "{" + ValidationCode.EMPTY_PASSWORD + "}")
	@Size(min = 8, max = 50, message = "Password should be minimum {min} characters and maximum {max} characters")
	@Pattern(regexp = Patterns.PASSWORD_PATTERN, message = "{" + ValidationCode.INVALID_PASSWORD + "}")
	private String newPassword;

}
