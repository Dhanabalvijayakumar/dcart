package com.dhanabal.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dhanabal.dto.CategoriesDTO;
import com.dhanabal.dto.OrderItemsDTO;
import com.dhanabal.dto.OrdersDTO;
import com.dhanabal.dto.ProductsDTO;
import com.dhanabal.dto.UsersDTO;
import com.dhanabal.entity.OrderItems;
import com.dhanabal.entity.Orders;
import com.dhanabal.exception.EcommerceException;
import com.dhanabal.repository.OrderItemsRepository;
import com.dhanabal.repository.OrdersRepository;

import jakarta.transaction.Transactional;

@Service(value = "ordersService")
@Transactional
public class OrdersServiceImpl implements OrdersService {
	
	@Autowired
	private OrdersRepository ordersRepo;
	
	@Autowired
	private OrderItemsRepository orderItemsRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<OrderItemsDTO> getOrderItems(Long userId) throws EcommerceException {
		
		List<Orders> ordersList = ordersRepo.findOrderByUserId(userId);
		
		if(ordersList.isEmpty()) {
			throw new EcommerceException("No orders for this user");
		}
		
		List<OrderItems> orderItemsList = new ArrayList<>();
		
		for(Orders order: ordersList) {
			List<OrderItems> orderItems = orderItemsRepo.findOrderItemsByOrderId(order.getOrderId());
			orderItemsList.addAll(orderItems);
		}
		
		List<OrderItemsDTO> orderItemsDTOlist = new ArrayList<>();
		
		for(OrderItems orderItems: orderItemsList) {
			
			OrderItemsDTO dto = new OrderItemsDTO();
			
			dto.setOrderItemId(orderItems.getOrderItemId());
			dto.setQuantity(orderItems.getQuantity());
			dto.setPrice(orderItems.getPrice());
			
			
//			OrdersDTO ordersDTO = new OrdersDTO();
//			ordersDTO.setOrderId(orderItems.getOrders().getOrderId());
//			ordersDTO.setTotalPrice(orderItems.getOrders().getTotalPrice());
//			ordersDTO.setStatus(orderItems.getOrders().getStatus());
//			ordersDTO.setCurrentTime(orderItems.getOrders().getCreatedTime());
			
			OrdersDTO ordersDTO = modelMapper.map(orderItems.getOrders(), OrdersDTO.class);
			
//			UsersDTO usersDTO = new UsersDTO();
//			usersDTO.setUserId(orderItems.getOrders().getUsers().getUserId());
//			usersDTO.setName(orderItems.getOrders().getUsers().getName());
//			usersDTO.setEmail(orderItems.getOrders().getUsers().getEmail());
//			usersDTO.setMobile(orderItems.getOrders().getUsers().getMobile());
//			usersDTO.setPassword(orderItems.getOrders().getUsers().getPassword());
//			usersDTO.setRole(orderItems.getOrders().getUsers().getRole());
//			usersDTO.setCreatedTime(orderItems.getOrders().getUsers().getCreatedTime());
			
			UsersDTO usersDTO = modelMapper.map(orderItems.getOrders().getUsers(), UsersDTO.class);
			
			ordersDTO.setUsersDTO(usersDTO);
			
			
			dto.setOrdersDTO(ordersDTO);
			
			
//			ProductsDTO productDTO = new ProductsDTO();
//			productDTO.setProductId(orderItems.getProduct().getProductId());
//			productDTO.setProductName(orderItems.getProduct().getProductName());
//			productDTO.setDescription(orderItems.getProduct().getDescription());
//			productDTO.setPrice(orderItems.getProduct().getPrice());
//			productDTO.setImageUrl(orderItems.getProduct().getImageUrl());
//			productDTO.setCreatedTime(orderItems.getProduct().getCreatedTime());
			
			ProductsDTO productDTO = modelMapper.map(orderItems.getProduct(), ProductsDTO.class);
			
//			CategoriesDTO categoryDTO = new CategoriesDTO();
//			categoryDTO.setCategoryId(orderItems.getProduct().getCategories().getCategoryId());
//			categoryDTO.setName(orderItems.getProduct().getCategories().getName());
			
			CategoriesDTO categoryDTO = modelMapper.map(orderItems.getProduct().getCategories(), CategoriesDTO.class);
			
			productDTO.setCategoriesDTO(categoryDTO);
			
			
			dto.setProductsDTO(productDTO);
			
			orderItemsDTOlist.add(dto);
			
		}
		
		return orderItemsDTOlist;
	}
	
	
	
}