package com.easybill.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.easybill.model.metadata.EnumConstant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class OrderInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	private Date orderDate;

	private String description;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = EnumConstant.STATUS, nullable = false)
	private EnumConstant.Status status;

	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORDERED_BY", nullable = false, updatable = false)
	private User orderedBy;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MODIFIED_BY")
	private User modifiedBy;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "orderInfo", cascade = CascadeType.PERSIST)
	private List<OrderDetail> orderDetails = new ArrayList<>();
	
}
