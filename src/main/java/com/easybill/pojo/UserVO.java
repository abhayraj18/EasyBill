package com.easybill.pojo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.validation.Errors;

import com.easybill.model.metadata.EnumConstant.UserType;
import com.easybill.validation.Patterns;
import com.easybill.validation.Validatable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserVO implements Validatable {

	private Integer id;

	@NotNull(message = "{name.empty}")
	@Size(min = 2, max = 50, message = "Name should be minimum {min} characters and maximum {max} characters")
	@Pattern(regexp = Patterns.NAME_PATTERN, message = "Name should only contain alphabets")
	private String name;

	@NotNull(message = "{email.empty}")
	@Size(min = 6, max = 50, message = "Email should be minimum {min} characters and maximum {max} characters")
	@Pattern(regexp = Patterns.EMAIL_PATTERN, message = "Please enter valid email")
	private String email;

	@NotNull(message = "{password.empty}")
	@Size(min = 8, max = 50, message = "Password should be minimum {min} characters and maximum {max} characters")
	@Pattern(regexp = Patterns.PASSWORD_PATTERN, message = "Password should contain at least one digit, one upper case letter, one lower case letter and one special symbol (!@#$%^&:_)")
	private String password;

	private String address;

	@NotNull(message = "{phoneNumber.empty}")
	@Size(min = 10, max = 10, message = "Phone number should have minimum {min} and maximum {max} digits")
	private String phoneNumber;

	@NotNull(message = "Please select user type")
	private String userType;

	@Override
	public void validate(Errors errors) {
		if (!isValidUserType()) {
			errors.reject("usertype.invalid");
		}
	}

	private boolean isValidUserType() {
		return UserType.WHOLESALER.toString().equalsIgnoreCase(getUserType())
				|| UserType.DISTRIBUTOR.toString().equalsIgnoreCase(getUserType());
	}

}
