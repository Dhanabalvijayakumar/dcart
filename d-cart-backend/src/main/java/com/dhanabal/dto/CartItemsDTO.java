package com.dhanabal.dto;

import lombok.Data;

@Data
public class CartItemsDTO {

	private Long cartItemId;
	
	private int quantity;
	
	private CartDTO cartDTO;
	
	private ProductsDTO productsDTO;
	
}