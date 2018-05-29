package com.easybill.pojo;

import java.util.Objects;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.validation.Errors;

import com.easybill.model.metadata.EnumConstant.Unit;
import com.easybill.validation.Patterns;
import com.easybill.validation.Validatable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemVO implements Validatable {

	private Integer id;

	@NotNull(message = "{name.empty}")
	@Size(min = 2, max = 50, message = "Name should be minimum {min} characters and maximum {max} characters")
	@Pattern(regexp = Patterns.ALPHANUMERIC_NAME_PATTERN, message = "Name should only contain alphabets and/or numbers")
	private String name;

	@NotNull(message = "{item.baseUnit.empty}")
	@NotEmpty(message = "{item.baseUnit.empty}")
	@Pattern(regexp = Patterns.UNIT_PATTERN, message = "Please select valid base unit")
	private String baseUnit;
	
	private Float baseUnitPrice;

	@NotNull(message = "{item.largeUnit.empty}")
	@NotEmpty(message = "{item.largeUnit.empty}")
	@Pattern(regexp = Patterns.UNIT_PATTERN, message = "Please select valid large unit")
	private String largeUnit;
	
	private Float largeUnitPrice;
	
	@NotNull(message = "{item.largeUnitInBaseUnit.null}")
	@Min(value = 1, message = "{item.largeUnitInBaseUnit.invalid}")
	private Integer largeUnitInBaseUnit;
	
	@Override
	public void validate(Errors errors) {
		if (isBaseUnitLargerThanOrEqualToLargeUnit()) {
			errors.reject("item.unit.conversion.invalid");
		}
		
		if (isInvalidItemPrice()) {
			errors.reject("item.unit.price.invalid");
		}
	}

	private boolean isInvalidItemPrice() {
		return (Objects.isNull(getBaseUnitPrice()) || getBaseUnitPrice() <= 0)
				&& (Objects.isNull(getLargeUnitPrice()) || getLargeUnitPrice() <= 0);
	}

	private boolean isBaseUnitLargerThanOrEqualToLargeUnit() {
		return Unit.valueOf(getBaseUnit()).getOrder() >= Unit.valueOf(getLargeUnit()).getOrder();
	}

}
