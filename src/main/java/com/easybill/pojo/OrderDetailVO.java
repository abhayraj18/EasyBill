package com.easybill.pojo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.easybill.validation.Patterns;
import com.easybill.validation.ValidationCode;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailVO {

	private Integer id;
	
	@NotNull(message = "{" + ValidationCode.INVALID_ORDER_ITEM_ID + "}")
	@Min(value = 1, message = "{" + ValidationCode.INVALID_ORDER_ITEM_ID + "}")
	private Integer itemId;

	@NotNull(message = "{" + ValidationCode.INVALID_QUANTITY + "}")
	@Min(value = 1, message = "{" + ValidationCode.INVALID_QUANTITY + "}")
	private Float quantity;
	
	@NotBlank(message = "{" + ValidationCode.INVALID_ORDER_ITEM_UNIT + "}")
	@Pattern(regexp = Patterns.UNIT_PATTERN, message = "{" + ValidationCode.INVALID_ORDER_ITEM_UNIT + "}")
	private String unit;

}
