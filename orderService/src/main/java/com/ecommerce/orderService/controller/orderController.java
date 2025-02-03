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

@RestController
@RequestMapping(value="/api/order")
public class orderController {
	
	@Autowired
	private OrderService orderService;
	
	
	@PostMapping(value="/placeOrder")
	public ResponseEntity<String> placeOrder(@RequestBody OrderDTO orderDTO ){
		System.out.println(orderDTO.toString());
		orderService.placeOrder(orderDTO);
		return new ResponseEntity<String>("Order Placed Successfully",HttpStatus.CREATED);
	}
}
