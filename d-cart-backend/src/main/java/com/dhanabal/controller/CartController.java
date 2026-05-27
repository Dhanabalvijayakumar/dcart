package com.dhanabal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dhanabal.dto.AddToCartRequest;
import com.dhanabal.dto.CartItemsDTO;
import com.dhanabal.dto.RemoveFromCart;
import com.dhanabal.exception.EcommerceException;
import com.dhanabal.service.CartService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "/api/cart")
public class CartController {

	@Autowired
	private CartService cartService;

//	@GetMapping(value = "/{userId}")
//	public ResponseEntity<List<CartItemsDTO>> getCartItems(@PathVariable Long userId) throws EcommerceException {
//		
//		List<CartItemsDTO> cartItemsListDTO = cartService.getCartItemsByCartId(userId);
//		return new ResponseEntity<>(cartItemsListDTO, HttpStatus.OK);
//		
//	}

	@GetMapping
	public ResponseEntity<List<CartItemsDTO>> getCartItems() throws EcommerceException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		Long userId = (Long) authentication.getPrincipal();

		List<CartItemsDTO> cartItemsListDTO = cartService.getCartItemsByCartId(userId);
		return new ResponseEntity<>(cartItemsListDTO, HttpStatus.OK);

	}

//	@GetMapping
//	public ResponseEntity<List<ProductsDTO>> getCartItems() throws EcommerceException {
//
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//		Long userId = (Long) authentication.getPrincipal();
//
//		List<ProductsDTO> cartItemsListDTO = cartService.getCartItemsByCartId(userId);
//		return new ResponseEntity<>(cartItemsListDTO, HttpStatus.OK);
//
//	}

	@PostMapping(value = "/add")
	public ResponseEntity<String> addProductToCart(@RequestBody AddToCartRequest request) throws EcommerceException {

		String successMsg = cartService.addItem(request.getUserId(), request.getProductId());
		return new ResponseEntity<>(successMsg, HttpStatus.OK);

	}

	@DeleteMapping(value = "/delete")
	public ResponseEntity<String> deleteProduct(@RequestBody RemoveFromCart request) throws EcommerceException {

		String successMsg = cartService.removeItem(request.getUserId(), request.getProductId());
		return new ResponseEntity<>(successMsg, HttpStatus.OK);

	}

//	@PostMapping(value = "/placeOrder/{userId}")
//	public ResponseEntity<String> placeOrder(@PathVariable Long userId) throws EcommerceException {
//		
//		String successMsg = cartService.placeOrder(userId);
//		return new ResponseEntity<>(successMsg, HttpStatus.OK);
//		
//	}

	@PostMapping(value = "/placeOrder")
	public ResponseEntity<String> placeOrder() throws EcommerceException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		Long userId = (Long) authentication.getPrincipal();

		String successMsg = cartService.placeOrder(userId);
		return new ResponseEntity<>(successMsg, HttpStatus.OK);

	}
	
	@PostMapping(value = "/placeDirectOrder/{productId}")
	public ResponseEntity<String> placeDirectOrder(@PathVariable Long productId) throws EcommerceException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		Long userId = (Long) authentication.getPrincipal();

		String successMsg = cartService.placeDirectOrder(userId, productId);
		return new ResponseEntity<>(successMsg, HttpStatus.OK);

	}

}
