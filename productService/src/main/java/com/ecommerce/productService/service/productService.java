package com.ecommerce.productService.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.productService.DTO.productDTO;
import com.ecommerce.productService.entity.Product;
import com.ecommerce.productService.repository.productRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class productService {
	
	@Autowired
	private productRepository productRepository;
	
	
	public void createNewProduct(productDTO productDTO) {
		//Builder method is used to create the object. Build method is used to written the instance.
		Product product = Product.builder()
				.name(productDTO.getName())
				.description(productDTO.getDescription())
				.price(productDTO.getPrice())
				.build();
		productRepository.save(product);
		log.info("Product "+ product.getId()+ " Saved Successfully");
		
	}


	public List<productDTO> getAllProducts() {
		
		List<Product> productList= (List<Product>) productRepository.findAll();
		
		return productList.stream().map(product -> mapToProductDTO(product)).toList();
	}
	
	public productDTO mapToProductDTO(Product product) {
	
		productDTO productsDTO= productDTO.builder()
				.id(product.getId())
				.name(product.getName())
				.description(product.getDescription())
				.price(product.getPrice())
				.build();
		
	return productsDTO;
	}

}
