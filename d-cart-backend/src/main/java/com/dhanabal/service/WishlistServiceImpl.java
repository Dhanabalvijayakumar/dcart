package com.dhanabal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dhanabal.dto.AddToWishlistDTO;
import com.dhanabal.dto.CategoriesDTO;
import com.dhanabal.dto.ProductsDTO;
import com.dhanabal.dto.RemoveFromWishlistDTO;
import com.dhanabal.entity.Products;
import com.dhanabal.entity.Users;
import com.dhanabal.entity.Wishlist;
import com.dhanabal.exception.EcommerceException;
import com.dhanabal.repository.ProductsRepository;
import com.dhanabal.repository.UsersRepository;
import com.dhanabal.repository.WishlistRepository;

import jakarta.transaction.Transactional;

@Service(value = "wishlistService")
@Transactional
public class WishlistServiceImpl implements WishlistService {

	@Autowired
	private WishlistRepository wishlistRepo;

	@Autowired
	private UsersRepository usersRepo;

	@Autowired
	private ProductsRepository productsRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private RecommendationService recommendationService;

	@Override
	public List<ProductsDTO> getWishListItems(Long userId) throws EcommerceException {

		List<Wishlist> wishlistItems = wishlistRepo.getWishlistByUserId(userId);

		if (wishlistItems.isEmpty()) {
//			throw new EcommerceException("No items in the wishlist for the user");
			List<ProductsDTO> wishlistList = new ArrayList<>();
			return wishlistList;
		}

		return wishlistItems.stream().map(li -> {

			ProductsDTO prodDTO = modelMapper.map(li.getProduct(), ProductsDTO.class);

//			CategoriesDTO categoryDTO = new CategoriesDTO();
//			categoryDTO.setCategoryId(li.getProduct().getCategories().getCategoryId());
//			categoryDTO.setName(li.getProduct().getCategories().getName());

			CategoriesDTO categoryDTO = modelMapper.map(li.getProduct().getCategories(), CategoriesDTO.class);

			prodDTO.setCategoriesDTO(categoryDTO);

			return prodDTO;

		}).toList();
	}

	@Override
	public Long addWishlist(AddToWishlistDTO dto) throws EcommerceException {

		Users user = usersRepo.findById(dto.getUserId()).orElseThrow(() -> new EcommerceException("User Not Found"));

		Products product = productsRepository.findById(dto.getProductId())
				.orElseThrow(() -> new EcommerceException("Product Not Found"));

		Optional<Wishlist> existingWishlist = wishlistRepo.findWishlistByUserAndProduct(dto.getUserId(),
				dto.getProductId());

		if (existingWishlist.isPresent()) {
			throw new EcommerceException("Product already in wishlist");
		}

		Wishlist wishlist = new Wishlist();
//		wishlist.setWishlistId(dto.getWishlistId());
		wishlist.setUser(user);
		wishlist.setProduct(product);

		wishlistRepo.save(wishlist);

		recommendationService.updateUserPreference(user.getUserId(), product.getCategories().getCategoryId(), 2);

		return wishlist.getWishlistId();
	}

	@Override
	public String removeWishlistProduct(RemoveFromWishlistDTO dto) throws EcommerceException {

		Products product = productsRepository.findById(dto.getProductId())
				.orElseThrow(() -> new EcommerceException("Product Not Found"));

		Users user = usersRepo.findById(dto.getUserId()).orElseThrow(() -> new EcommerceException("User Not Found"));

		int numberOfRowsDeleted = wishlistRepo.removeWishlistByProductIdAndUserId(dto.getProductId(), dto.getUserId());

		if (numberOfRowsDeleted > 0) {
			return "Product with ID:" + dto.getProductId() + " is successfully deleted";
		} else {
			return "Product is not in wishlist for the user";
		}
	}

}
