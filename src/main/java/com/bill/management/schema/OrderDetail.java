package com.bill.management.schema;

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

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class OrderDetail {
	
	public enum Unit {
	    PKT, BOX, BAG
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne
	@JoinColumn(name = "itemId")
	private Item item;
	private int quantity;
	
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "enum ('PKT', 'BOX', 'BAG')", nullable = false)
	private Unit unit;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orderId")
	private Order order;
	
}
