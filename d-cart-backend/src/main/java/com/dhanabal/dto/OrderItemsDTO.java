package com.dhanabal.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OrderItemsDTO {

	private Long orderItemId;
	
	private int quantity;
	
	private BigDecimal price;
	
	private OrdersDTO ordersDTO;
	
//	private List<ProductsDTO> productsListDTO;
	
	private ProductsDTO productsDTO;
	
}