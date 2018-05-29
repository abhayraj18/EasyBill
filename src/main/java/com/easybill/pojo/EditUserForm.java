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
public class EditUserForm {

	@NotNull(message = "{id.null}")
	@Min(value = 1, message = "{id.invalid}")
	private Integer id;

	@NotNull(message = "{name.empty}")
	@Size(min = 2, max = 50, message = "Name should be minimum {min} characters and maximum {max} characters")
	@Pattern(regexp = Patterns.ALPHABETIC_NAME_PATTERN, message = "Name should only contain alphabets")
	private String name;

	@NotNull(message = "{email.empty}")
	@Size(min = 6, max = 50, message = "Email should be minimum {min} characters and maximum {max} characters")
	@Pattern(regexp = Patterns.EMAIL_PATTERN, message = "Please enter valid email")
	private String email;

	private String address;

	@NotNull(message = "{phoneNumber.empty}")
	@Size(min = 10, max = 10, message = "Phone number should have minimum {min} and maximum {max} digits")
	private String phoneNumber;

}
