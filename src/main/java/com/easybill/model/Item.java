package com.easybill.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.easybill.model.metadata.EnumConstant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false, length = 100)
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = EnumConstant.UNITS, nullable = false)
	private EnumConstant.Unit baseUnit;

	@Column(nullable = false, columnDefinition = "float(7,2)")
	private Float baseUnitPrice;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = EnumConstant.UNITS, nullable = false)
	private EnumConstant.Unit largeUnit;

	@Column(nullable = false, columnDefinition = "float(7,2)")
	private Float largeUnitPrice;
	
	private Integer largeUnitInBaseUnit;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date addedAt;

	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedAt;

	@Column(columnDefinition = "bit(1) default b'0'")
	private boolean archive;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Column(nullable = false)
	private User addedBy;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Column(nullable = false)
	private User modifiedBy;
}
