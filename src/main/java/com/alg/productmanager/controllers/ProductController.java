package com.alg.productmanager.controllers;

import com.alg.productmanager.objects.dtos.ProductDto;
import com.alg.productmanager.objects.entities.Product;
import com.alg.productmanager.service.ProductService;
import com.alg.productmanager.converters.ProductConverter;
import com.alg.productmanager.exceptions.ProductDoesNotExistException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/product")
public class ProductController {

    private final ProductService productService;
    private final ProductConverter productConverter;

    @PostMapping(value = "/add", produces = "application/json")
    public ResponseEntity<Product> addProduct(@RequestBody @Valid ProductDto productDto) {

        log.info("Creating new product with name: " + productDto.getName());

        var product = productConverter.toEntity(productDto);
        product = productService.add(product);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping(value = "get/{id}", produces = "application/json")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) throws ProductDoesNotExistException {

        log.info("Searching for product with id: " + id);

        var product = productService.find(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PutMapping(value = "/edit/{id}", produces = "application/json")
    public ResponseEntity<Product> editProductForm(@PathVariable Long id, @RequestBody @Valid ProductDto productDto) throws ProductDoesNotExistException {

        log.info("Altering product with id: " + id);

        var existingProduct = productService.edit(id, productDto);
        return new ResponseEntity<>(existingProduct, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) throws ProductDoesNotExistException {
        log.info("Deleting product with id: " + id);

        productService.delete(id);
        return ResponseEntity.ok().body("product with id: " + id + " deleted");
    }
}
