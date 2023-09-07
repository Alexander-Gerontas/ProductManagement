package com.alg.productmanager.utils;

import com.alg.productmanager.objects.dtos.ProductDto;

public final class ProductDtoFactory {
    public static ProductDto getProductDto() {
        var productDto = new ProductDto("pr_name", "pr_description", 5.00);
        return productDto;
    }

    public static ProductDto getAlteredProductDto() {
        var productDto = new ProductDto("alt_name", "alt_description", 10.00);
        return productDto;
    }
}
