package com.ecommerce.productService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.productService.DTO.productDTO;
import com.ecommerce.productService.service.productService;

@RestController
@RequestMapping(value="/api/product")
public class productController {
	
	@Autowired
	private productService productService;
	
	@PostMapping(value="/create")
	public ResponseEntity<String> createProduct(@RequestBody productDTO productDTO){
		productService.createNewProduct(productDTO);
		return new ResponseEntity<String>("Product Created Successfully", HttpStatus.CREATED);
		
	}
	
	@GetMapping(value="/view")
	public ResponseEntity<List<productDTO>> viewAllProduct(){
		
		List<productDTO> productsList=productService.getAllProducts();
		return new ResponseEntity<List<productDTO>>(productsList, HttpStatus.OK);
	}

	
}
