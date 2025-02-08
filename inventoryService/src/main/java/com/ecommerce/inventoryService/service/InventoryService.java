package com.ecommerce.inventoryService.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.inventoryService.DTO.InventoryResponse;
import com.ecommerce.inventoryService.repository.inventoryRepository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {

	private final inventoryRepository inventoryRepository;
	
	@Transactional(readOnly=true) // Since it is a getting data from database we put readOnly is true
	public List<InventoryResponse> getStockStatus(List<String> skuCode) {
		
		return inventoryRepository.findByskuCodeIn(skuCode).stream()
				.map(inventory -> InventoryResponse.builder()
						.skuCode(inventory.getSkuCode())
						.isInStock(inventory.getQuantity()>0)
						.quantity(inventory.getQuantity())
						.build()
				)
				.toList();
	
	}

}
