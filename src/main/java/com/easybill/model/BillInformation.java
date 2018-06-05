package com.easybill.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class BillInformation {

	@Id
    @GeneratedValue(generator = "foreigngen")
    @GenericGenerator(strategy = "foreign", name="foreigngen",
            parameters = @Parameter(name = "property", value="orderInfo"))
	private Integer orderInfoId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date billedAt;

	@Column(nullable = false, columnDefinition = "float(9,2)")
	private Float amount;

	@Column(columnDefinition = "float(9,2)")
	private Float pendingAmount;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "billInformation")
	private OrderInfo orderInfo;

}
