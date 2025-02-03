package com.ecommerce.orderService.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.orderService.DTO.OrderDTO;
import com.ecommerce.orderService.DTO.OrderLineItemsDTO;
import com.ecommerce.orderService.entity.Order;
import com.ecommerce.orderService.entity.OrderLineItems;
import com.ecommerce.orderService.repository.OrderRepository;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	public void placeOrder(OrderDTO orderDTO) {
		Order order = new Order();
		order.setOrderNumber(UUID.randomUUID().toString());
		System.out.println(orderDTO.toString());
		List<OrderLineItems> orderLineItems =orderDTO.getOrderLineItemsDTOs()
				                               .stream().map(orderLineItemsDTO -> mapToDTO(orderLineItemsDTO))
				                               .toList();
		order.setOrderLineItemsList(orderLineItems);
		orderRepository.save(order);
	}
	private OrderLineItems mapToDTO(OrderLineItemsDTO orderLineItemsDTO) {
		OrderLineItems orderLineItems = new OrderLineItems();
		orderLineItems.setPrice(orderLineItemsDTO.getPrice());
		orderLineItems.setQuantity(orderLineItemsDTO.getQuantity());
		orderLineItems.setSkuCode(orderLineItemsDTO.getSkuCode());
		return orderLineItems;
	}

}
