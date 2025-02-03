package com.ecommerce.orderService.DTO;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDTO {
	
	private List<OrderLineItemsDTO> orderLineItemsDTOs;

}
