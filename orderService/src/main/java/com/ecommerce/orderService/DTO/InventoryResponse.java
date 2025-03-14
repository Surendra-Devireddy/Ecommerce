package com.ecommerce.orderService.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponse {
	
	private String skuCode;
	private boolean isInStock;
	private Integer quantity;
	private boolean stockAvailable;

}
