package com.easybill.pojo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.easybill.validation.Patterns;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordForm {

	@NotNull(message = "{id.null}")
	@Min(value = 1, message = "{id.invalid}")
	private Integer id;

	@NotNull(message = "Please enter current password")
	private String currentPassword;
	
	@NotNull(message = "{password.empty}")
	@Size(min = 8, max = 50, message = "Password should be minimum {min} characters and maximum {max} characters")
	@Pattern(regexp = Patterns.PASSWORD_PATTERN, message = "{password.invalid}")
	private String newPassword;

}
