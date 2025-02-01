package com.ecommerce.productService.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.productService.entity.Product;

@Repository
public interface productRepository extends CrudRepository<Product, Integer>{

}
