package com.dhanabal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.dhanabal.entity.Wishlist;

public interface WishlistRepository extends CrudRepository<Wishlist, Long> {

	@Query("select w from Wishlist w where w.user.userId = :userId")
	List<Wishlist> getWishlistByUserId(@Param("userId") Long userId);

	@Modifying
	@Query("delete from Wishlist w where w.product.productId = :productId and w.user.userId = :userId")
	int removeWishlistByProductIdAndUserId(@Param("productId") Long productId, @Param("userId") Long userId);

	@Query("select w from Wishlist w where w.user.userId= :userId and w.product.productId= :productId")
	Optional<Wishlist> findWishlistByUserAndProduct(@Param("userId") Long userId, @Param("productId") Long productId);

}