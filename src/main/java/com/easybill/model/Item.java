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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.easybill.model.metadata.EnumConstant;
import com.easybill.model.metadata.EnumConstant.Unit;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false, length = 100, unique = true)
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
	
	@Column(nullable = false)
	private Integer unitConversionValue;

	@Column(columnDefinition = "bit(1) default b'0'")
	private boolean archive;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ADDED_BY", nullable = false, updatable = false)
	private User addedBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	private Date addedAt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MODIFIED_BY")
	private User modifiedBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedAt;

	public boolean isValidUnit(Unit unit) {
		return unit == getBaseUnit() || unit == getLargeUnit();
	}

}
