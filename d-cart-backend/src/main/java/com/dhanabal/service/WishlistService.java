package com.dhanabal.service;

import java.util.List;

import com.dhanabal.dto.AddToWishlistDTO;
import com.dhanabal.dto.ProductsDTO;
import com.dhanabal.dto.RemoveFromWishlistDTO;
import com.dhanabal.exception.EcommerceException;

public interface WishlistService {

//	List<WishlistDTO> getWishListItems(Long userId) throws EcommerceException;

	List<ProductsDTO> getWishListItems(Long userId) throws EcommerceException;

	Long addWishlist(AddToWishlistDTO dto) throws EcommerceException;

	String removeWishlistProduct(RemoveFromWishlistDTO dto) throws EcommerceException;

}
