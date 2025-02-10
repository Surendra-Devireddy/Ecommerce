package com.ecommerce.orderService.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.orderService.DTO.OrderDTO;
import com.ecommerce.orderService.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value="/api/order")
@Tag(
		name="Rest APIs to place order",
		description="API to place order"
		)
public class orderController {
	
	@Autowired
	private OrderService orderService;
	
	@Operation(
			summary="Create Order API",
			description="This API creates product in the Ecommerce database"
			)
	@ApiResponse(
			responseCode="201",
			description="HTTP Status Created"
			)
	@PostMapping(value="/placeOrder")
	public ResponseEntity<String> placeOrder(@RequestBody OrderDTO orderDTO ){
		
		orderService.placeOrder(orderDTO);
		//return new ResponseEntity<String>("Order Placed Successfully",HttpStatus.CREATED);
		//Response Entity follows the builder pattern to chain the methods and create the instance easily
		return ResponseEntity.status(HttpStatus.CREATED).body("Order Placed Successfully");
	}
}
