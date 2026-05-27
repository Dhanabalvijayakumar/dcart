package com.dhanabal.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dhanabal.dto.CartDTO;
import com.dhanabal.dto.CartItemsDTO;
import com.dhanabal.dto.CategoriesDTO;
import com.dhanabal.dto.ProductsDTO;
import com.dhanabal.dto.Status;
import com.dhanabal.dto.UsersDTO;
import com.dhanabal.entity.Cart;
import com.dhanabal.entity.CartItems;
import com.dhanabal.entity.OrderItems;
import com.dhanabal.entity.Orders;
import com.dhanabal.entity.Products;
import com.dhanabal.entity.Users;
import com.dhanabal.exception.EcommerceException;
import com.dhanabal.repository.CartItemsRepository;
import com.dhanabal.repository.CartRepository;
import com.dhanabal.repository.OrderItemsRepository;
import com.dhanabal.repository.OrdersRepository;
import com.dhanabal.repository.ProductsRepository;
import com.dhanabal.repository.UsersRepository;

import jakarta.transaction.Transactional;

@Service(value = "cartService")
@Transactional
public class CartServiceImpl implements CartService {

	@Autowired
	private UsersRepository usersRepo;

	@Autowired
	private ProductsRepository productsRepo;

	@Autowired
	private CartRepository cartRepo;

	@Autowired
	private CartItemsRepository cartItemsRepo;

	@Autowired
	private OrdersRepository ordersRepo;

	@Autowired
	private OrderItemsRepository orderItemsRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private RecommendationService recommendationService;

	@Override
	public List<CartItemsDTO> getCartItemsByCartId(Long userId) throws EcommerceException {

//		Cart cart = cartRepo.findCartByUserId(userId)
//				.orElseThrow(() -> new EcommerceException("No cart found for the user"));

		Users user = usersRepo.findById(userId).orElseThrow(() -> new EcommerceException("User not found"));

		Cart cart = cartRepo.findCartByUserId(userId).orElseGet(() -> {
			Cart newCart = new Cart();
			newCart.setUsers(user);
			return cartRepo.save(newCart);
		});

		List<CartItems> cartItemsList = cartItemsRepo.findCartItemsByCartId(cart.getCartId());

//		if (cartItemsList.isEmpty()) {
//			throw new EcommerceException("No items in the cart");
//		}

		List<CartItemsDTO> cartItemsListDTO = new ArrayList<>();

		for (CartItems cartItems : cartItemsList) {

			if (cartItems.getQuantity() <= 0) {
//				cartItemsRepo.delete(cartItems);
				continue;
			}

//			CartItemsDTO dto = new CartItemsDTO();
//			dto.setCartItemId(cartItems.getCartItemId());
//			dto.setQuantity(cartItems.getQuantity());

			CartItemsDTO dto = modelMapper.map(cartItems, CartItemsDTO.class);

			CartDTO cartDTO = new CartDTO();
			cartDTO.setCartId(cartItems.getCart().getCartId());

//			UsersDTO userDTO = new UsersDTO();
//			userDTO.setUserId(user.getUserId());
//			userDTO.setName(user.getName());
//			userDTO.setEmail(user.getEmail());
//			userDTO.setEmail(user.getEmail());
//			userDTO.setMobile(user.getMobile());
//			userDTO.setPassword(user.getPassword());
//			userDTO.setRole(user.getRole());
//			userDTO.setCreatedTime(user.getCreatedTime());

			UsersDTO userDTO = modelMapper.map(user, UsersDTO.class);

			cartDTO.setUsersDTO(userDTO);

			dto.setCartDTO(cartDTO);

//			ProductsDTO product = new ProductsDTO();
//			product.setProductId(cartItems.getProducts().getProductId());
//			product.setProductName(cartItems.getProducts().getProductName());
//			product.setDescription(cartItems.getProducts().getDescription());
//			product.setPrice(cartItems.getProducts().getPrice());
//			product.setImageUrl(cartItems.getProducts().getImageUrl());
//			product.setCreatedTime(cartItems.getProducts().getCreatedTime());

			ProductsDTO product = modelMapper.map(cartItems.getProducts(), ProductsDTO.class);

//			CategoriesDTO category = new CategoriesDTO();
//			category.setCategoryId(cartItems.getProducts().getCategories().getCategoryId());
//			category.setName(cartItems.getProducts().getCategories().getName());

			CategoriesDTO category = modelMapper.map(cartItems.getProducts().getCategories(), CategoriesDTO.class);

			product.setCategoriesDTO(category);

			dto.setProductsDTO(product);

			cartItemsListDTO.add(dto);

		}

		return cartItemsListDTO;
	}

//	@Override
//	public List<ProductsDTO> getCartItemsByCartId(Long userId) throws EcommerceException {
//
////		Cart cart = cartRepo.findCartByUserId(userId)
////				.orElseThrow(() -> new EcommerceException("No cart found for the user"));
//
//		Users user = usersRepo.findById(userId).orElseThrow(() -> new EcommerceException("User not found"));
//
//		Cart cart = cartRepo.findCartByUserId(userId).orElseGet(() -> {
//			Cart newCart = new Cart();
//			newCart.setUsers(user);
//			return cartRepo.save(newCart);
//		});
//
//		List<CartItems> cartItemsList = cartItemsRepo.findCartItemsByCartId(cart.getCartId());
//
//		if (cartItemsList.isEmpty()) {
//			throw new EcommerceException("No items in the cart");
//		}
//
//		List<ProductsDTO> productsListDTO = new ArrayList<>();
//
//		for (CartItems cartItems : cartItemsList) {
//
//			if (cartItems.getQuantity() <= 0) {
////				cartItemsRepo.delete(cartItems);
//				continue;
//			}
//
//
//			
//			ProductsDTO product = modelMapper.map(cartItems.getProducts(), ProductsDTO.class);
//
////			CategoriesDTO category = new CategoriesDTO();
////			category.setCategoryId(cartItems.getProducts().getCategories().getCategoryId());
////			category.setName(cartItems.getProducts().getCategories().getName());
//			
//			CategoriesDTO category = modelMapper.map(cartItems.getProducts().getCategories(), CategoriesDTO.class);
//
//			product.setCategoriesDTO(category);
//			
//			productsListDTO.add(product);
//
//		}
//
//		return productsListDTO;
//	}

	@Override
	public String addItem(Long userId, Long productId) throws EcommerceException {

		Users user = usersRepo.findById(userId).orElseThrow(() -> new EcommerceException("User not found"));

		Products product = productsRepo.findById(productId)
				.orElseThrow(() -> new EcommerceException("Product not found"));

		Cart cart = cartRepo.findCartByUserId(userId).orElseGet(() -> {
			Cart newCart = new Cart();
			newCart.setUsers(user);
			return cartRepo.save(newCart);
		});

		Optional<CartItems> existingItem = cartItemsRepo.findCartItemsByCartIdAndProductId(cart.getCartId(), productId);

		if (existingItem.isPresent()) {

			CartItems item = existingItem.get();
			item.setQuantity(item.getQuantity() + 1);

			cartItemsRepo.save(item);

			recommendationService.updateUserPreference(userId, product.getCategories().getCategoryId(), 3);

			return "Item added and Quantity updated as " + item.getQuantity();

		} else {

			CartItems newItem = new CartItems();
			newItem.setCart(cart);
			newItem.setProducts(product);
			newItem.setQuantity(1);

			cartItemsRepo.save(newItem);

			recommendationService.updateUserPreference(userId, product.getCategories().getCategoryId(), 3);

			return "Item added successfully with ID: " + newItem.getCartItemId();
		}

	}

	@Override
	public String removeItem(Long userId, Long productId) throws EcommerceException {

		Users user = usersRepo.findById(userId).orElseThrow(() -> new EcommerceException("User not found"));

//		Products product = productsRepo.findById(productId)
//				.orElseThrow(() -> new EcommerceException("Product not found"));

		Cart cart = cartRepo.findCartByUserId(userId).orElseGet(() -> {
			Cart newCart = new Cart();
			newCart.setUsers(user);
			return cartRepo.save(newCart);
		});

		Optional<CartItems> existingItem = cartItemsRepo.findCartItemsByCartIdAndProductId(cart.getCartId(), productId);

		if (existingItem.isPresent()) {
			CartItems item = existingItem.get();

			int updatedQuantity = item.getQuantity() - 1;

			if (updatedQuantity <= 0) {
				cartItemsRepo.delete(item);
				return "Product removed from cart successfully";
			}

			item.setQuantity(updatedQuantity);

			cartItemsRepo.save(item);

			return "Quantity updated";
		} else {
			return "No product found in the cart";
		}

	}

	private BigDecimal getDiscountedPrice(Products product) {

		BigDecimal price = product.getPrice();

		BigDecimal discountPercentage = BigDecimal.valueOf(product.getDiscountPercentage());

		BigDecimal discountAmount = price.multiply(discountPercentage).divide(BigDecimal.valueOf(100));

		return price.subtract(discountAmount);
	}

	@Override
	public String placeOrder(Long userId) throws EcommerceException {

		Users user = usersRepo.findById(userId).orElseThrow(() -> new EcommerceException("User not found"));

		Cart cart = cartRepo.findCartByUserId(userId).orElseGet(() -> {
			Cart newCart = new Cart();
			newCart.setUsers(user);
			return cartRepo.save(newCart);
		});

		List<CartItems> existingItems = cartItemsRepo.findCartItemsByCartId(cart.getCartId());

		if (existingItems.isEmpty()) {
			return "Cart is empty";
		}

		BigDecimal total = BigDecimal.ZERO;

		for (CartItems item : existingItems) {

			Products product = productsRepo.findById(item.getProducts().getProductId())
					.orElseThrow(() -> new EcommerceException("Product not found"));

			BigDecimal discountedPrice = getDiscountedPrice(product);

			BigDecimal itemTotal = discountedPrice.multiply(BigDecimal.valueOf(item.getQuantity()));

			total = total.add(itemTotal);

		}

		Orders order = new Orders();
		order.setTotalPrice(total);
		order.setStatus(Status.PLACED);
		order.setUsers(user);
		order.setCreatedTime(LocalDateTime.now());

		ordersRepo.save(order);

		for (CartItems item : existingItems) {

			Products product = productsRepo.findById(item.getProducts().getProductId())
					.orElseThrow(() -> new EcommerceException("Product not found"));

			BigDecimal discountedPrice = getDiscountedPrice(product);

			OrderItems orderItem = new OrderItems();
			orderItem.setOrders(order);
			orderItem.setProduct(product);
			orderItem.setQuantity(item.getQuantity());

			BigDecimal itemPrice = discountedPrice.multiply(BigDecimal.valueOf(item.getQuantity()));

			orderItem.setPrice(itemPrice);

			orderItemsRepo.save(orderItem);

			recommendationService.updateUserPreference(userId, product.getCategories().getCategoryId(), 5);

		}

		cartItemsRepo.deleteAll(existingItems);
		cartRepo.delete(cart);

		return "Order placed successfully";
	}

	@Override
	public String placeDirectOrder(Long userId, Long productId) throws EcommerceException {

		Users user = usersRepo.findById(userId).orElseThrow(() -> new EcommerceException("User not found"));

		Products product = productsRepo.findById(productId)
				.orElseThrow(() -> new EcommerceException("Product not found"));

		BigDecimal discountedPrice = getDiscountedPrice(product);

		Orders order = new Orders();

		order.setUsers(user);
		order.setStatus(Status.PLACED);
		order.setCreatedTime(LocalDateTime.now());
		order.setTotalPrice(discountedPrice);

		ordersRepo.save(order);

		OrderItems orderItem = new OrderItems();

		orderItem.setOrders(order);
		orderItem.setProduct(product);
		orderItem.setQuantity(1);
		orderItem.setPrice(discountedPrice);

		orderItemsRepo.save(orderItem);

		recommendationService.updateUserPreference(userId, product.getCategories().getCategoryId(), 5);

		return "order placed successfully";
	}

}
