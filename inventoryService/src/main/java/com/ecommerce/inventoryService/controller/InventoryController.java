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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value="/api/inventory")
@RequiredArgsConstructor
@Tag(
		name="Rest APIs for Inventory",
		description="API to check and decrease the inventory"
		)
public class InventoryController {
	
	private final InventoryService inventoryService;
	@Operation(
			summary="Stock Checking",
			description="This API is to check the product availability in the Ecommerce database"
			)
	@ApiResponse(
			responseCode="200",
			description="HTTP Status ok"
			)
	
	@GetMapping(value="/search")
	@ResponseStatus(HttpStatus.OK)
	public List<InventoryResponse> isStockAvailable(@RequestParam(value="skuCode") List<String> skuCode) {
		//Spring automaticall converts the url to a list
		System.out.println("Inside Inventory method");
		return inventoryService.getStockStatus(skuCode);
	}
	
	@Operation(
			summary="Quantity reduction",
			description="API to reduce the product quantity in the Ecommerce database"
			)
	@ApiResponse(
			responseCode="200",
			description="HTTP Status ok"
			)
	@PatchMapping(value="/quantityReduction")
	@ResponseStatus(HttpStatus.OK)
	public void quantityReduction(@RequestBody Map<String,Integer> inventoryData) {
		System.out.println("Inside Inventory");
		inventoryService.reduceQuantity(inventoryData);
	}

}
