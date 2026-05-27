package com.dhanabal.service;

import java.util.List;

import com.dhanabal.dto.CartItemsDTO;
import com.dhanabal.exception.EcommerceException;

public interface CartService {

	List<CartItemsDTO> getCartItemsByCartId(Long userId) throws EcommerceException;

//	List<ProductsDTO> getCartItemsByCartId(Long userId) throws EcommerceException;

	String addItem(Long userId, Long productId) throws EcommerceException;

	String removeItem(Long userId, Long productId) throws EcommerceException;

	String placeOrder(Long userId) throws EcommerceException;

	String placeDirectOrder(Long userId, Long productId) throws EcommerceException;

}
