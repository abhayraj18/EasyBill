package com.easybill.pojo;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.easybill.validation.ValidationCode;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditOrderVO {

	@NotNull(message = "{" + ValidationCode.INVALID_ID + "}")
	@Min(value = 1, message = "{" + ValidationCode.INVALID_ID + "}")
	private Integer id;

	private String description;
	
	private boolean approve;
	
	@NotNull(message = "{" + ValidationCode.EMPTY_ORDER + "}")
	@Size(min = 1, message = "{" + ValidationCode.EMPTY_ORDER + "}")
	@Valid
	private Map<Integer, OrderDetailVO> orderDetails;
	
	private List<Integer> deletedItemList;
	
}
