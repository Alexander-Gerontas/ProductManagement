package com.alg.productmanager.service;

import com.alg.productmanager.exceptions.GenericError;
import com.alg.productmanager.exceptions.ProductDoesNotExistException;
import com.alg.productmanager.objects.dtos.ProductDto;
import com.alg.productmanager.objects.entities.Product;
import com.alg.productmanager.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  @Override
  public Product add(Product product) {
    return productRepository.save(product);
  }

  public Product find(Long id) throws ProductDoesNotExistException {

    // get product from db
    Product product = productRepository.findProductById(id);

    // throw exception if it does not exist
    if (product == null) {
      throw new ProductDoesNotExistException(GenericError.PRDOUCT_DOES_NOT_EXIST, id);
    }

    return product;
  }

  public Product edit(Long id, ProductDto productDto) throws ProductDoesNotExistException {

    // get product from db
    Product existingProduct = productRepository.findProductById(id);

    if (existingProduct == null) {
      throw new ProductDoesNotExistException(GenericError.PRDOUCT_DOES_NOT_EXIST, id);
    }

    // transfer dto properties to existing product
    existingProduct.setName(productDto.getName());
    existingProduct.setDescription(productDto.getDescription());
    existingProduct.setName(productDto.getName());

    // save updated product
    productRepository.save(existingProduct);

    return existingProduct;
  }

  public void delete(Long id) throws ProductDoesNotExistException {

    // get product from db
    Product existingProduct = productRepository.findProductById(id);

    // throw exception if it does not exist
    if (existingProduct == null) {
      throw new ProductDoesNotExistException(GenericError.PRDOUCT_DOES_NOT_EXIST, id);
    }

    // delete the product
    productRepository.deleteById(id);
  }
}
