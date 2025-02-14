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

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
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
	@CircuitBreaker(name="Inventory", fallbackMethod="FallBackResponseMethod") //For whatever the API calls that are happening inside the circuit breaker logic will be applied
	public ResponseEntity<String> placeOrder(@RequestBody OrderDTO orderDTO ){
		
		orderService.placeOrder(orderDTO);
		//return new ResponseEntity<String>("Order Placed Successfully",HttpStatus.CREATED);
		//Response Entity follows the builder pattern to chain the methods and create the instance easily
		return ResponseEntity.status(HttpStatus.CREATED).body("Order Placed Successfully");
	}
	
	//For the fallbackmethod have the same return type and same method signature also have runtimeexception parameter to pass the exception that we are getting from above method
	//If Any of the run time exceptions comes from the above method then the fall back will called
	//But breaker is not open yet it gets opened after reaching the threshold
	//Resilience4j only counts failures when an exception is thrown 
	public ResponseEntity<String> FallBackResponseMethod(OrderDTO orderDTO, RuntimeException runTimeException) {
		return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Oops! Something went wrong, Please order some time!");
	}
}
