package com.dhanabal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.dhanabal.entity.OrderItems;

public interface OrderItemsRepository extends CrudRepository<OrderItems, Long> {

	@Query("select o from OrderItems o where o.orders.orderId = :orderId")
	List<OrderItems> findOrderItemsByOrderId(@Param("orderId") Long orderId);
	
}