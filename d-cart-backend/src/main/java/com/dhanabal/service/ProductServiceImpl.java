package com.dhanabal.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dhanabal.dto.CategoriesDTO;
import com.dhanabal.dto.ProductsDTO;
import com.dhanabal.entity.Products;
import com.dhanabal.exception.EcommerceException;
import com.dhanabal.repository.ProductsRepository;

import jakarta.transaction.Transactional;

@Service(value = "productService")
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductsRepository productsRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private RecommendationService recommendationService;

// 		@Override
// 		public List<ProductsDTO> getAllProducts() throws EcommerceException {
	//
// 			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	//
// 			Long userId = (Long) authentication.getPrincipal();
	//
// 			List<Products> productList = recommendationService.recommendProducts(userId);
	//
// 			return productList.stream().map(prod -> {
	//
// 				ProductsDTO dto = modelMapper.map(prod, ProductsDTO.class);
	//
// 				CategoriesDTO categoryDTO = modelMapper.map(prod.getCategories(), CategoriesDTO.class);
	//
// 				dto.setCategoriesDTO(categoryDTO);
	//
// 				return dto;
	//
// 			}).toList();
// 		}

	@Override
	public Page<ProductsDTO> getAllProducts(int page, int size) throws EcommerceException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		Long userId = (Long) authentication.getPrincipal();

		Pageable pageable = PageRequest.of(page, size);

		List<Products> productList = recommendationService.recommendProducts(userId);

		int start = (int) pageable.getOffset();
		int end = Math.min(start + pageable.getPageSize(), productList.size());

		List<Products> pagedProducts = productList.subList(start, end);

		List<ProductsDTO> dtoList = pagedProducts.stream().map((prod) -> {

			ProductsDTO dto = modelMapper.map(prod, ProductsDTO.class);
			CategoriesDTO categoryDTO = modelMapper.map(prod.getCategories(), CategoriesDTO.class);
			dto.setCategoriesDTO(categoryDTO);

			return dto;

		}).toList();

		return new PageImpl<>(dtoList, pageable, productList.size());

	}

	@Override
	public ProductsDTO getProductByProductId(Long productId) throws EcommerceException {
		Optional<Products> product = productsRepo.findById(productId);
		if (product.isPresent()) {
			ProductsDTO productDTO = modelMapper.map(product, ProductsDTO.class);
			CategoriesDTO categoryDTO = modelMapper.map(product.get().getCategories(), CategoriesDTO.class);
			productDTO.setCategoriesDTO(categoryDTO);
			return productDTO;
		} else {
			throw new EcommerceException("Product not found");
		}
	}

}
