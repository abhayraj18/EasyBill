package com.easybill.model;

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
import javax.persistence.OneToOne;

import com.easybill.model.metadata.EnumConstant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class OrderDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToOne
	@JoinColumn(name = "itemId", nullable = false)
	private Item item;

	@Column(nullable = false, columnDefinition = "float(5,2)")
	private Float quantity;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = EnumConstant.UNITS, nullable = false)
	private EnumConstant.Unit unit;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orderInfoId", nullable = false)
	private OrderInfo orderInfo;

}
