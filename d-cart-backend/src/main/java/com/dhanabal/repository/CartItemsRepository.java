package com.dhanabal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.dhanabal.entity.CartItems;

public interface CartItemsRepository extends CrudRepository<CartItems, Long> {

	@Query("select c from CartItems c where c.cart.cartId = :cartId")
	List<CartItems> findCartItemsByCartId(@Param("cartId") Long cartId);
	
	@Query("select c from CartItems c where c.cart.cartId = :cartId and c.products.productId = :productId")
	Optional<CartItems> findCartItemsByCartIdAndProductId(@Param("cartId") Long cartId, @Param("productId") Long productId);
	
}
