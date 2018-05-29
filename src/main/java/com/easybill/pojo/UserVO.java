package com.easybill.pojo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;

import com.easybill.model.metadata.EnumConstant.UserType;
import com.easybill.validation.Patterns;
import com.easybill.validation.Validatable;
import com.easybill.validation.ValidationCode;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserVO implements Validatable {

	private Integer id;

	@NotNull(message = "{" + ValidationCode.EMPTY_NAME + "}")
	@Size(min = 2, max = 50, message = "Name should be minimum {min} characters and maximum {max} characters")
	@Pattern(regexp = Patterns.ALPHABETIC_NAME_PATTERN, message = "{" + ValidationCode.INVALID_NAME + "}")
	private String name;

	@NotNull(message = "{" + ValidationCode.EMPTY_EMAIL + "}")
	@Size(min = 6, max = 50, message = "Email should be minimum {min} characters and maximum {max} characters")
	@Pattern(regexp = Patterns.EMAIL_PATTERN, message = "{" + ValidationCode.INVALID_EMAIL + "}")
	private String email;

	@NotNull(message = "{" + ValidationCode.EMPTY_PASSWORD + "}")
	@Size(min = 8, max = 50, message = "Password should be minimum {min} characters and maximum {max} characters")
	@Pattern(regexp = Patterns.PASSWORD_PATTERN, message = "{" + ValidationCode.INVALID_PASSWORD + "}")
	private String password;

	private String address;

	@NotNull(message = "{" + ValidationCode.EMPTY_PHONE_NUMBER + "}")
	@Size(min = 10, max = 10, message = "Phone number should have minimum {min} and maximum {max} digits")
	private String phoneNumber;

	@NotBlank(message = "{" + ValidationCode.EMPTY_USER_TYPE + "}")
	private String userType;

	@Override
	public void validate(Errors errors) {
		if (StringUtils.isNotBlank(getUserType()) && !isValidUserType()) {
			errors.reject(ValidationCode.INVALID_USER_TYPE);
		}
	}

	private boolean isValidUserType() {
		return UserType.WHOLESALER.toString().equalsIgnoreCase(getUserType())
				|| UserType.DISTRIBUTOR.toString().equalsIgnoreCase(getUserType());
	}

}
