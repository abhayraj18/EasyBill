package com.easybill.pojo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.easybill.validation.ValidationCode;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentForm {

	@NotNull(message = "{" + ValidationCode.INVALID_BILL_ID + "}")
	@Min(value = 1, message = "{" + ValidationCode.INVALID_BILL_ID + "}")
	private Integer billId;

	@NotNull(message = "{" + ValidationCode.INVALID_BILL_AMOUNT + "}")
	@Min(value = 1, message = "{" + ValidationCode.INVALID_BILL_AMOUNT + "}")
	private Float amount;

	private String description;

	private boolean approve;
}
