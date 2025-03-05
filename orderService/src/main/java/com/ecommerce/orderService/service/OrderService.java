package com.ecommerce.orderService.service;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;


import com.ecommerce.orderService.DTO.InventoryResponse;
import com.ecommerce.orderService.DTO.OrderDTO;
import com.ecommerce.orderService.DTO.OrderLineItemsDTO;
import com.ecommerce.orderService.entity.Order;
import com.ecommerce.orderService.entity.OrderLineItems;
import com.ecommerce.orderService.events.OrderPlacedEvent;
import com.ecommerce.orderService.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	private final WebClient.Builder webClientBuilder;
	private final Tracer tracer; //To generate the custom spanIds
	private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;
	
	public String placeOrder(OrderDTO orderDTO) {
		Order order = new Order();
		order.setOrderNumber(UUID.randomUUID().toString());
		
		List<OrderLineItems> orderLineItems =orderDTO.getOrderLineItemsDTOs()
				                               .stream().map(orderLineItemsDTO -> mapToDTO(orderLineItemsDTO))
				                               .toList();
		order.setOrderLineItemsList(orderLineItems);
		
		List<String> skuCodes = order.getOrderLineItemsList().stream()
								.map(orderItem -> orderItem.getSkuCode()).toList();
		
		Span inventoryServiceLookUp=tracer.nextSpan().name("InventoryServiceLookUp");
		
		try(Tracer.SpanInScope spanInScope= tracer.withSpan(inventoryServiceLookUp.start())){
			
			System.out.println("Inside try");
		
		//Communicate with the Inventory service to check the availability of product
		//Webclient is a latest HTTP Client in the Spring boot supports sync, async, streaming srevices
		//Since we are using service discovery we dont have to put local host we can just use appplication name
		 InventoryResponse[] inventoryResponseArray= webClientBuilder.build().get()
					.uri("http://inventoryservice/api/inventory/search", 
							uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build()) //To pass the query param
					.retrieve()       // to get the response
					.bodyToMono(InventoryResponse[].class)  // To convert the response to the array
					.block(); // To make the synchronous call
		 
		 List<Boolean> response =orderDTO.getOrderLineItemsDTOs()
                 .stream().map(orderLineItemsDTO -> checkAvailability(orderLineItemsDTO,inventoryResponseArray ))
                 .toList();
		
		 boolean allProductsinStock = response.stream().allMatch(n ->n.equals(true));
		 
		// boolean allProductsinStock=Arrays.stream(inventoryResponseArray)
			//	                          .allMatch(inventoryResponse -> inventoryResponse.isInStock());
		 //All match works like if all isInStock is true then it returns true
		 
		if(allProductsinStock) {
			System.out.println("Line 1");
		orderRepository.save(order);
		
		//Making a synchronous call to inventory service to decrease the inventory quantity based on the order items
		Map<String, Integer> inventoryData= new HashMap<>();
		for(OrderLineItems orderLineItem : orderLineItems) {
			inventoryData.put(orderLineItem.getSkuCode(),orderLineItem.getQuantity());
		}
		
		webClientBuilder.build().patch()
								.uri("http://inventoryservice/api/inventory/quantityReduction")
								.bodyValue(inventoryData)  // Automatically parse the value to json
								.retrieve()
								.bodyToMono(Void.class)
								.block();
		//Kafka template will send object as message to the notification topic
		
		System.out.println("Line 2");
		
		kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(order.getOrderNumber()));
		return "Order placed successfully";
		
		}else {
			throw new IllegalArgumentException("product not in stock");
		}
	  }finally {
		  inventoryServiceLookUp.end();
	  }
	}
	private boolean checkAvailability(OrderLineItemsDTO orderLineItemsDTO, InventoryResponse[] inventoryResponseArray) {
		for(int i=0; i< inventoryResponseArray.length;i++) {
			if(orderLineItemsDTO.getSkuCode().equals(inventoryResponseArray[i].getSkuCode())) {
				
				if(orderLineItemsDTO.getQuantity()<=inventoryResponseArray[i].getQuantity()) {
					
					return true;	
				}
			}
		}
		return false;
	}
	private OrderLineItems mapToDTO(OrderLineItemsDTO orderLineItemsDTO) {
		OrderLineItems orderLineItems = new OrderLineItems();
		orderLineItems.setPrice(orderLineItemsDTO.getPrice());
		orderLineItems.setQuantity(orderLineItemsDTO.getQuantity());
		orderLineItems.setSkuCode(orderLineItemsDTO.getSkuCode());
		return orderLineItems;
	}

}
