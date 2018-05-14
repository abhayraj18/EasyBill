package com.easybill.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.easybill.model.metadata.EnumConstant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class OrderInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false, unique = true, length = 50)
	private String orderNumber;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date orderDate;

	private String description;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = EnumConstant.STATUS, nullable = false)
	private EnumConstant.Status status;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date modifiedOn;

	@Column(nullable = false)
	Integer userId;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "orderInfo")
	private List<OrderDetail> orderDetails;

}