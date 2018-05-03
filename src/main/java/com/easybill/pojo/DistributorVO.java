package com.easybill.pojo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.validation.Errors;

import com.easybill.validation.Patterns;
import com.easybill.validation.Validatable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DistributorVO implements Validatable {

	private Integer id;
	@NotNull(message = "{name.empty}")
	@Size(min = 5, max = 10, message = "Length should be minimum {min} characters and maximum {max} characters")
	@Pattern(regexp = Patterns.NAME_PATTERN, message = "Name should only contain alphabets")
	private String name;
	private String address;
	@NotNull(message = "{phoneNumber.empty}")
	private String phoneNumber;

	@Override
	public void validate(Errors errors) {
	}

}
