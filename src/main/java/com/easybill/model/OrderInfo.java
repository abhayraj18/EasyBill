package com.easybill.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.easybill.exception.BillAmountNotPaidException;

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
	private Date orderedAt;

	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedAt;
	
	@Column(columnDefinition = "bit(1) default b'0'")
	private boolean approved;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORDERED_BY", nullable = false, updatable = false)
	private User orderedBy;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MODIFIED_BY")
	private User modifiedBy;
	
	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private BillInformation billInformation;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "orderInfo", cascade = CascadeType.PERSIST)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<OrderDetail> orderDetails = new ArrayList<>();

	public boolean isBillAmountPaid() throws BillAmountNotPaidException {
		if (Objects.nonNull(getBillInformation()) && Objects.nonNull(getBillInformation().getPendingAmount())
				&& getBillInformation().getPendingAmount() > 0) {
			throw new BillAmountNotPaidException("Bill amount is not paid for order: " + this.getId()
					+ ", pending amount = Rs. " + getBillInformation().getPendingAmount());
		}
		return true;
	}
	
}
