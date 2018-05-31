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
public class EditUserForm {

	@NotNull(message = "{" + ValidationCode.INVALID_ID + "}")
	@Min(value = 1, message = "{" + ValidationCode.INVALID_ID + "}")
	private Integer id;

	@NotNull(message = "{" + ValidationCode.EMPTY_NAME + "}")
	@Size(min = 2, max = 50, message = "Name should be minimum {min} characters and maximum {max} characters")
	@Pattern(regexp = Patterns.ALPHABETIC_NAME_PATTERN, message = "{" + ValidationCode.INVALID_NAME + "}")
	private String name;

	@NotNull(message = "{" + ValidationCode.EMPTY_EMAIL + "}")
	@Size(min = 6, max = 50, message = "Email should be minimum {min} characters and maximum {max} characters")
	@Pattern(regexp = Patterns.EMAIL_PATTERN, message = "{" + ValidationCode.INVALID_EMAIL + "}")
	private String email;

	private String address;

	@NotNull(message = "{" + ValidationCode.EMPTY_PHONE_NUMBER + "}")
	@Size(min = 10, max = 10, message = "Phone number should have minimum {min} and maximum {max} digits")
	private String phoneNumber;

}
