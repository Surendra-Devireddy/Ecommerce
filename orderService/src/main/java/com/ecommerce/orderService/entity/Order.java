package com.ecommerce.orderService.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="Orders")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String orderNumber;
	@OneToMany(cascade = CascadeType.ALL) //This will make the a foreignkey of orderLIneitmes in order table
	private List<OrderLineItems> orderLineItemsList;
	
}
