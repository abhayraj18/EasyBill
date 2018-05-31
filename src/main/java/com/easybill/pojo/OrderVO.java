package com.easybill.pojo;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.easybill.validation.ValidationCode;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderVO {

	private Integer id;

	private String description;
	
	@NotNull(message = "{" + ValidationCode.EMPTY_ORDER + "}")
	@Size(min = 1, message = "{" + ValidationCode.EMPTY_ORDER + "}")
	@Valid
	private List<OrderDetailVO> orderDetails;

}
