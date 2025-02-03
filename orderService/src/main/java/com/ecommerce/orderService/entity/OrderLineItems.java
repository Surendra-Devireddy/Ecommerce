package com.ecommerce.orderService.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="order_line_items")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineItems {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String skuCode;
	private double price;
	private Integer quantity;
	

}
