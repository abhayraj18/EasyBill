package com.easybill.pojo;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.easybill.validation.Validatable;

public class DistributorVO implements Validatable {

	private Integer id;
	private String name;
	private String address;
	private String phoneNumber;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public void validate(Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "name", "name.empty", "Name cannot be empty");
	}

}
