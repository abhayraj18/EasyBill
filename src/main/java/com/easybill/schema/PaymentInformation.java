package com.easybill.schema;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class PaymentInformation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date paymentDate;

	@Column(nullable = false, columnDefinition = "float(7,2)")
	private Float amount;

	private String description;

	private boolean approved;

	@Temporal(TemporalType.TIMESTAMP)
	private Date approvalDate;

	@ManyToOne
	@JoinColumn(name = "billId")
	private BillInformation billInformation;
}
