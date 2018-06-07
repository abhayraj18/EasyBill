package com.easybill.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.easybill.util.DateUtil;

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
	private Date paidAt;

	@Column(nullable = false, columnDefinition = "float(9,2)")
	private Float amount;

	private String description;

	@Column(columnDefinition = "bit(1) default b'0'")
	private boolean approved;

	@Temporal(TemporalType.TIMESTAMP)
	private Date approvedAt;

	@ManyToOne
	@JoinColumn(name = "billId")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private BillInformation billInformation;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPROVED_BY")
	private User approvedBy;
	
	public void approve(User user) {
		setApproved(true);
		setApprovedAt(DateUtil.getCurrentTime());
		setApprovedBy(user);
	}
}
