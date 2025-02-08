package com.ecommerce.inventoryService.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.inventoryService.entity.Inventory;

@Repository
public interface inventoryRepository extends CrudRepository<Inventory, Long>{

	
	/*
	@Query("select i from Inventory i where skuCode= :skuCode") // Here use entity class name and entity class variable not the table name and column name
	Optional<Inventory> findByskuCode(@Param(value="skuCode")String skuCode);
	*/
	
	List<Inventory> findByskuCodeIn(List<String> skuCode);
}
