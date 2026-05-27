package com.dhanabal.dto;

import lombok.Data;

@Data
public class AddToCartRequest {

	private Long userId;
	private Long productId;

}