package com.easybill.schema;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class BillInformation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false, unique = true, length = 50)
	private String billNumber;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date billingDate;

	@Column(nullable = false, columnDefinition = "float(7,2)")
	private Float amount;

	@Column(columnDefinition = "float(7,2)")
	private Float pendingAmount;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orderId", nullable = false)
	private Order order;

	@Column(nullable = false)
	Integer customerId;

}
