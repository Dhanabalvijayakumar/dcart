package com.dhanabal.dto;

import lombok.Data;

@Data
public class WishlistDTO {

	private Long wishlistId;

	private UsersDTO usersDTO;

	private ProductsDTO productsDTO;

}