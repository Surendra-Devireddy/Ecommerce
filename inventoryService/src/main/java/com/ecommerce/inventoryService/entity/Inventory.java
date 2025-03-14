package com.ecommerce.inventoryService.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity

@Data
@Table(name="inventory")
public class Inventory {
	
	/*
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	*/
	@Id
	private String skuCode;
	private Integer quantity;
	
	

}
