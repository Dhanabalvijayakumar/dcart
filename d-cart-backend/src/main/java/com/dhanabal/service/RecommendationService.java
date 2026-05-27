package com.dhanabal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dhanabal.entity.Categories;
import com.dhanabal.entity.Products;
import com.dhanabal.entity.UserPreference;
import com.dhanabal.entity.Users;
import com.dhanabal.repository.ProductsRepository;
import com.dhanabal.repository.UserPreferenceRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RecommendationService {

	@Autowired
	private UserPreferenceRepository userPreferenceRepo;

	@Autowired
	private ProductsRepository productsRepo;

	public void updateUserPreference(Long userId, Long categoryId, int weight) {

		System.out.println("Update Preference is called");

		Optional<UserPreference> optional = userPreferenceRepo.findByUserIdAndCategoryId(userId, categoryId);

		if (optional.isPresent()) {

			UserPreference userPreference = optional.get();

			userPreference.setScore(userPreference.getScore() + weight);

			userPreferenceRepo.save(userPreference);

		} else {

			UserPreference userPreference = new UserPreference();

			Users user = new Users();
			user.setUserId(userId);

			Categories category = new Categories();
			category.setCategoryId(categoryId);

			userPreference.setUser(user);
			userPreference.setCategory(category);
			userPreference.setScore(weight);

			userPreferenceRepo.save(userPreference);
		}
	}

//	public List<Products> recommendProducts(Long userId) {
//
//		List<UserPreference> userPreferences = userPreferenceRepo.findByUser_UserIdByOrderByScoreDesc(userId);
//
//		List<Long> categoryIds = userPreferences.stream().map(pref -> pref.getCategory().getCategoryId()).toList();
//
////		List<Products> prioritizedProducts = productsRepo.findByCategoryIds(categoryIds);
//
//		List<Products> prioritizedProducts = new ArrayList<>();
//
//		for (Long categoryId : categoryIds) {
//			List<Products> products = productsRepo.findByCategoriesCategoryId(categoryId);
//			prioritizedProducts.addAll(products);
//		}
//
//		List<Products> remainingProducts = productsRepo.findRemainingProducts(categoryIds);
//
//		prioritizedProducts.addAll(remainingProducts);
//
//		return prioritizedProducts;
//
//	}

	public List<Products> recommendProducts(Long userId) {

		List<UserPreference> userPreferences = userPreferenceRepo.findByUser_UserIdByOrderByScoreDesc(userId);

		List<Long> categoryIds = userPreferences.stream().map(pref -> pref.getCategory().getCategoryId()).toList();

//				List<Products> prioritizedProducts = productsRepo.findByCategoryIds(categoryIds);

		List<Products> prioritizedProducts = new ArrayList<>();

		for (Long categoryId : categoryIds) {
			List<Products> products = productsRepo.findByCategoriesCategoryId(categoryId);
			prioritizedProducts.addAll(products);
		}

		List<Products> remainingProducts = productsRepo.findRemainingProducts(categoryIds);

		prioritizedProducts.addAll(remainingProducts);

		return prioritizedProducts;

	}
}
