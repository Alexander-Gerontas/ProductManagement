package com.alg.productmanager.service;

import com.alg.productmanager.objects.dtos.ProductDto;
import com.alg.productmanager.objects.entities.Product;
import com.alg.productmanager.exceptions.ProductDoesNotExistException;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {

  /** adds a product in the database */
  Product add(Product product);

  /** retrieves a product from the database by id */
  Product find(Long id) throws ProductDoesNotExistException;

  /** alters a product properties */
  Product edit(Long id, ProductDto productDto) throws ProductDoesNotExistException;

  /** deletes a product from the database */
  void delete(Long id) throws ProductDoesNotExistException;
}
