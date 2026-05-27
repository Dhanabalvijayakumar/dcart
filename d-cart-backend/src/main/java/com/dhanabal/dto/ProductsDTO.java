package com.dhanabal.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ProductsDTO {

	private Long productId;
	
	private String productName;
	
	private String description;
	
	private BigDecimal price;
	
	private String brand;
	
	private Double rating;
	
	private Double discountPercentage;
	
	private String imageUrl;
	
	private LocalDateTime createdTime;
	
	private CategoriesDTO categoriesDTO;
	
}