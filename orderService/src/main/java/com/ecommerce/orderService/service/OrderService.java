package com.ecommerce.orderService.service;


import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;


import com.ecommerce.orderService.DTO.InventoryResponse;
import com.ecommerce.orderService.DTO.OrderDTO;
import com.ecommerce.orderService.DTO.OrderLineItemsDTO;
import com.ecommerce.orderService.entity.Order;
import com.ecommerce.orderService.entity.OrderLineItems;
import com.ecommerce.orderService.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	private final WebClient webClient;
	
	public void placeOrder(OrderDTO orderDTO) {
		Order order = new Order();
		order.setOrderNumber(UUID.randomUUID().toString());
		
		List<OrderLineItems> orderLineItems =orderDTO.getOrderLineItemsDTOs()
				                               .stream().map(orderLineItemsDTO -> mapToDTO(orderLineItemsDTO))
				                               .toList();
		order.setOrderLineItemsList(orderLineItems);
		
		List<String> skuCodes = order.getOrderLineItemsList().stream()
								.map(orderItem -> orderItem.getSkuCode()).toList();
		
	
		
		
		//Communicate with the Inventory service to check the availability of product
		//Webclient is a latest HTTP Client in the Spring boot supports sync, async, streaming srevices
		 InventoryResponse[] inventoryResponseArray= webClient.get()
					.uri("http://localhost:8082/api/inventory/search", 
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
			
		orderRepository.save(order);
		}else {
			throw new IllegalArgumentException("product not in stock");
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
