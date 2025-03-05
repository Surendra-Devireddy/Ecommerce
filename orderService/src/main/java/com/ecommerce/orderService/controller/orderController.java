package com.ecommerce.orderService.controller;


import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.orderService.DTO.OrderDTO;
import com.ecommerce.orderService.service.OrderService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
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
	@ResponseStatus(HttpStatus.CREATED)
	@CircuitBreaker(name="Inventory", fallbackMethod="FallBackResponseMethod") //For whatever the API calls that are happening inside the circuit breaker logic will be applied
	@TimeLimiter(name="Inventory")
	@Retry(name="Inventory")
	public CompletableFuture<String> placeOrder(@RequestBody OrderDTO orderDTO ){
		
		//Now the place order will run in a seperate thread
		return CompletableFuture.supplyAsync(()-> orderService.placeOrder(orderDTO));
		//return new ResponseEntity<String>("Order Placed Successfully",HttpStatus.CREATED);
		//Response Entity follows the builder pattern to chain the methods and create the instance easily
		//Completable future is for making the asynchrnous calls by creating the new thread without disturbing the main thread.
		//Time limiter will throw the timeout exception after passing the certain time.
		//Time Limiter will work only with the completable future return type i.e for aysnchrnouscalls 
	
	}
	
	//For the fallbackmethod have the same return type and same method signature also have runtimeexception parameter to pass the exception that we are getting from above method
	//If Any of the run time exceptions comes from the above method then the fall back will called
	//But breaker is not open yet it gets opened after reaching the threshold
	//Resilience4j only counts failures when an exception is thrown 
	
	public CompletableFuture<String> FallBackResponseMethod(OrderDTO orderDTO, RuntimeException runTimeException) {
		return CompletableFuture.supplyAsync(()-> "Oops! Something went wrong, Please order some time later!");
	}
}
