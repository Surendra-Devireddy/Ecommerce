package com.ecommerce.orderService.DTO;

import lombok.Data;

@Data

public class OrderLineItemsDTO {
	
	private long id;
	
	private String skuCode;
	
	private double price;
	
	private Integer quantity;

}
