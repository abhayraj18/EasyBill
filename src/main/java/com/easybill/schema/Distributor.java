package com.easybill.schema;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.easybill.schema.metadata.EnumConstant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Distributor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false, length = 100)
	private String name;

	private String address;

	@Column(nullable = false, length = 15)
	private String phoneNumber;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = EnumConstant.STATUS, nullable = false)
	private EnumConstant.Status status;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date createdOn;

}
