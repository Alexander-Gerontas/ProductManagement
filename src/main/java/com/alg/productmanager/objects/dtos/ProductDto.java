package com.alg.productmanager.objects.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Product dto
 */
@Getter
@ToString
@AllArgsConstructor
public class ProductDto {

	@NotEmpty
	private String name;

	@NotEmpty
	private String description;

	@NotNull
	@Positive(message = "value must be positive")
	private double price;
}
