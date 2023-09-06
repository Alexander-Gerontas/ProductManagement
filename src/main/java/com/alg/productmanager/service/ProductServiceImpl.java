package com.alg.productmanager.service;

import com.alg.productmanager.objects.dtos.ProductDto;
import com.alg.productmanager.objects.entities.Product;
import com.alg.productmanager.repository.ProductRepository;
import com.alg.productmanager.exceptions.GenericError;
import com.alg.productmanager.exceptions.ProductDoesNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  @Override
  public Product add(Product product) {
    return productRepository.save(product);
  }

  public Product find(Long id) throws ProductDoesNotExistException {

    Product product = productRepository.findProductById(id);

    if (product == null) {
      throw new ProductDoesNotExistException(GenericError.PRDOUCT_DOES_NOT_EXIST, id);
    }

    return product;
  }

  public Product edit(Long id, ProductDto productDto) throws ProductDoesNotExistException {

    Product existingProduct = productRepository.findProductById(id);

    if (existingProduct == null) {
      throw new ProductDoesNotExistException(GenericError.PRDOUCT_DOES_NOT_EXIST, id);
    }

    existingProduct.setName(productDto.getName());
    existingProduct.setDescription(productDto.getDescription());
    existingProduct.setName(productDto.getName());

    productRepository.save(existingProduct);

    return existingProduct;
  }

  public void delete(Long id) throws ProductDoesNotExistException {

    Product existingProduct = productRepository.findProductById(id);

    if (existingProduct == null) {
      throw new ProductDoesNotExistException(GenericError.PRDOUCT_DOES_NOT_EXIST, id);
    }

    productRepository.deleteById(id);
  }
}
