package com.ecommerce.orderService.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.orderService.entity.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long>{

}
