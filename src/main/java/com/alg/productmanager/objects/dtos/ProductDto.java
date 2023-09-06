package com.alg.productmanager.objects.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

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
