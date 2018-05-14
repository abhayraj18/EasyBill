package com.easybill.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("DISTRIBUTOR")
public class Distributor extends User {

	protected Distributor() {
	}

	public Distributor(String name, String username, String email, String password, String address,
			String phoneNumber) {
		super(name, username, email, password, address, phoneNumber);
	}

}
