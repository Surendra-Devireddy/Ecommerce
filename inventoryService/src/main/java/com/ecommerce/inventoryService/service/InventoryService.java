package com.ecommerce.inventoryService.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecommerce.inventoryService.DTO.InventoryResponse;
import com.ecommerce.inventoryService.entity.Inventory;
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
	
	public Integer getQuantity(String skuCode) {
		 Optional<Inventory> inventory=inventoryRepository.findById(skuCode);
		return inventory.get().getQuantity();
	}

	public void reduceQuantity(Map<String,Integer> inventoryData) {
		for(Map.Entry<String,Integer> entry: inventoryData.entrySet()) {
		Inventory inventory = new Inventory();
		inventory.setSkuCode(entry.getKey());
		inventory.setQuantity(getQuantity(inventory.getSkuCode()) - entry.getValue());
		inventoryRepository.save(inventory);
		
	}

}
}
