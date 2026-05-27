package com.dhanabal.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.dhanabal.entity.Products;

public interface ProductsRepository extends CrudRepository<Products, Long> {
	
	Page<Products> findAll(Pageable pageable);

	@Query("select p from Products p where p.categories.categoryId in :categoryIds")
	List<Products> findByCategoryIds(@Param("categoryIds") List<Long> categoryIds);

	@Query("select p from Products p where p.categories.categoryId not in :categoryIds")
	List<Products> findRemainingProducts(@Param("categoryIds") List<Long> categoryIds);

	List<Products> findByCategoriesCategoryId(Long categoryId);

}
