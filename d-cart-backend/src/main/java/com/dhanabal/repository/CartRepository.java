package com.dhanabal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.dhanabal.entity.Cart;

public interface CartRepository extends CrudRepository<Cart, Long> {

	@Query("select c from Cart c where c.users.userId = :userId")
	Optional<Cart> findCartByUserId( @Param("userId") Long userId);
	
}
