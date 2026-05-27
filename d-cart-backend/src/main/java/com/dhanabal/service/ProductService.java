package com.dhanabal.service;

import org.springframework.data.domain.Page;

import com.dhanabal.dto.ProductsDTO;
import com.dhanabal.exception.EcommerceException;

public interface ProductService {

//	List<ProductsDTO> getAllProducts() throws EcommerceException;
	
	Page<ProductsDTO> getAllProducts(int page, int size) throws EcommerceException;
	
	ProductsDTO getProductByProductId(Long productId) throws EcommerceException;
	
}
