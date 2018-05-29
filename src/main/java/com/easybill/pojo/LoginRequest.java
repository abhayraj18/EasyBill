package com.easybill.pojo;

import javax.validation.constraints.NotBlank;

import com.easybill.validation.ValidationCode;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

	@NotBlank(message = "{" + ValidationCode.EMPTY_USERNAME + "}")
	private String username;

	@NotBlank(message = "{" + ValidationCode.EMPTY_PASSWORD + "}")
	private String password;

}
