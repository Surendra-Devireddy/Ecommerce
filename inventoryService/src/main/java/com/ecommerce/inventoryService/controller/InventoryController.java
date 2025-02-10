package com.ecommerce.inventoryService.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.inventoryService.DTO.InventoryResponse;
import com.ecommerce.inventoryService.service.InventoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value="/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
	
	private final InventoryService inventoryService;
	
	@GetMapping(value="/search")
	@ResponseStatus(HttpStatus.OK)
	public List<InventoryResponse> isStockAvailable(@RequestParam(value="skuCode") List<String> skuCode) {
		//Spring automaticall converts the url to a list
		return inventoryService.getStockStatus(skuCode);
	}
	
	@PatchMapping(value="/quantityReduction")
	@ResponseStatus(HttpStatus.OK)
	public void quantityReduction(@RequestBody Map<String,Integer> inventoryData) {
		inventoryService.reduceQuantity(inventoryData);
	}

}
