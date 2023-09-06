package com.alg.productmanager.converters;

import com.alg.productmanager.objects.dtos.ProductDto;
import com.alg.productmanager.objects.entities.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** Converts Product DTOs to domain objects and vice versa. */
@Component
@RequiredArgsConstructor
public class ProductConverter {

	public Product toEntity(ProductDto productDto) {

		return new Product(productDto.getName(), productDto.getDescription(), productDto.getPrice());
	}
}
