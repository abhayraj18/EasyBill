package com.easybill.pojo;

import java.util.Objects;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.validation.Errors;

import com.easybill.util.CommonUtil;
import com.easybill.validation.Patterns;
import com.easybill.validation.Validatable;
import com.easybill.validation.ValidationCode;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemVO implements Validatable {

	private Integer id;

	@NotNull(message = "{" + ValidationCode.EMPTY_NAME + "}")
	@Size(min = 2, max = 50, message = "Name should be minimum {min} characters and maximum {max} characters")
	@Pattern(regexp = Patterns.ALPHANUMERIC_NAME_PATTERN, message = "{" + ValidationCode.INVALID_ITEM_NAME + "}")
	private String name;

	@NotBlank(message = "{" + ValidationCode.INVALID_ITEM_BASE_UNIT + "}")
	@Pattern(regexp = Patterns.UNIT_PATTERN, message = "{" + ValidationCode.INVALID_ITEM_BASE_UNIT + "}")
	private String baseUnit;
	
	private Float baseUnitPrice;

	@NotBlank(message = "{" + ValidationCode.INVALID_ITEM_LARGE_UNIT + "}")
	@Pattern(regexp = Patterns.UNIT_PATTERN, message = "{" + ValidationCode.INVALID_ITEM_LARGE_UNIT + "}")
	private String largeUnit;
	
	private Float largeUnitPrice;
	
	@NotNull(message = "{" + ValidationCode.NULL_UNIT_CONVERSION_VALUE + "}")
	@Min(value = 1, message = "{" + ValidationCode.INVALID_UNIT_CONVERSION_VALUE + "}")
	private Integer unitConversionValue;
	
	@Override
	public void validate(Errors errors) {
		if (isBaseUnitLargerThanOrEqualToLargeUnit()) {
			errors.reject(ValidationCode.INVALID_UNIT_CONVERSION);
		}
		
		if (isInvalidItemPrice()) {
			errors.reject(ValidationCode.INVALID_UNIT_PRICE);
		}
	}

	private boolean isInvalidItemPrice() {
		return (Objects.isNull(getBaseUnitPrice()) || getBaseUnitPrice() <= 0)
				&& (Objects.isNull(getLargeUnitPrice()) || getLargeUnitPrice() <= 0);
	}

	private boolean isBaseUnitLargerThanOrEqualToLargeUnit() {
		if (CommonUtil.isValidUnit(getBaseUnit()) && CommonUtil.isValidUnit(getLargeUnit())) {
			return CommonUtil.getUnit(getBaseUnit()).getOrder() >= CommonUtil.getUnit(getLargeUnit()).getOrder();
		}
		return false;
	}

}
