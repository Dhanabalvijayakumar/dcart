package com.dhanabal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dhanabal.dto.ProductsDTO;
import com.dhanabal.exception.EcommerceException;
import com.dhanabal.service.ProductService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "/api/product")
public class ProductsController {

	@Autowired
	private ProductService productService;

//	@GetMapping(value = "/loadProducts")
//	public ResponseEntity<List<ProductsDTO>> getAllProducts() throws EcommerceException {
//
//		List<ProductsDTO> productList = productService.getAllProducts();
//		return new ResponseEntity<>(productList, HttpStatus.OK);
//
//	}

	@GetMapping(value = "/loadProducts")
	public ResponseEntity<Page<ProductsDTO>> getAllProducts(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) throws EcommerceException {

		Page<ProductsDTO> productList = productService.getAllProducts(page, size);
		return new ResponseEntity<>(productList, HttpStatus.OK);

	}

	@GetMapping(value = "/{productId}")
	public ResponseEntity<ProductsDTO> getProductById(@PathVariable Long productId) throws EcommerceException {

		ProductsDTO product = productService.getProductByProductId(productId);
		return new ResponseEntity<>(product, HttpStatus.OK);

	}

}
