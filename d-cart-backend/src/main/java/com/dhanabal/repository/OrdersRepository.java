package com.dhanabal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.dhanabal.entity.Orders;

public interface OrdersRepository extends CrudRepository<Orders, Long> {

	@Query("select o from Orders o where o.users.userId = :userId")
	List<Orders> findOrderByUserId(@Param("userId") Long userId);
	
}
