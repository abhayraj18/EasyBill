package com.easybill.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.easybill.model.metadata.EnumConstant.UserType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("WHOLESALER")
public class Wholesaler extends User {

	protected Wholesaler() {
	}

	public Wholesaler(String name, String username, String email, String address, String phoneNumber) {
		super(name, username, email, address, phoneNumber);
		setType(UserType.WHOLESALER);
	}

}
