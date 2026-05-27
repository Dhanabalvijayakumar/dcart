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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dhanabal.dto.AddToWishlistDTO;
import com.dhanabal.dto.ProductsDTO;
import com.dhanabal.dto.RemoveFromWishlistDTO;
import com.dhanabal.exception.EcommerceException;
import com.dhanabal.service.WishlistService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "/api/wishlist")
public class WishlistController {

	@Autowired
	private WishlistService wishlistService;

//	@GetMapping(value = "/{userId}")
//	public ResponseEntity<List<WishlistDTO>> getWishlistByUser(@PathVariable Long userId) throws EcommerceException {
//
//		List<WishlistDTO> wishListItems = wishlistService.getWishListItems(userId);
//		return new ResponseEntity<>(wishListItems, HttpStatus.OK);
//
//	}

	@GetMapping
	public ResponseEntity<List<ProductsDTO>> getWishlistByUser() throws EcommerceException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		Long userId = (Long) authentication.getPrincipal();

		List<ProductsDTO> wishListItems = wishlistService.getWishListItems(userId);
		return new ResponseEntity<>(wishListItems, HttpStatus.OK);

	}

	@PostMapping(value = "/add")
	public ResponseEntity<String> addItemToWishlist(@RequestBody AddToWishlistDTO dto) throws EcommerceException {

		Long wishlistId = wishlistService.addWishlist(dto);
		String successMsg = "Successfully added to wishlist with ID: " + wishlistId;
		return new ResponseEntity<>(successMsg, HttpStatus.OK);

	}

	@DeleteMapping(value = "/delete")
	public ResponseEntity<String> deleteProductById(@RequestBody RemoveFromWishlistDTO dto) throws EcommerceException {

		String successMsg = wishlistService.removeWishlistProduct(dto);
		return new ResponseEntity<>(successMsg, HttpStatus.OK);

	}

}
