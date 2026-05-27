package com.dhanabal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dhanabal.dto.OrderItemsDTO;
import com.dhanabal.exception.EcommerceException;
import com.dhanabal.service.OrdersService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "/api/orders")
public class OrdersController {

	@Autowired
	private OrdersService ordersService;

//	@GetMapping(value = "/{userId}")
//	public ResponseEntity<List<OrderItemsDTO>> getOrderItemsByUserId(@PathVariable Long userId) throws EcommerceException {
//		
//		List<OrderItemsDTO> orderItemsList = ordersService.getOrderItems(userId);
//		return new ResponseEntity<>(orderItemsList, HttpStatus.OK);
//		
//	}

	@GetMapping
	public ResponseEntity<List<OrderItemsDTO>> getOrderItemsByUserId() throws EcommerceException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		Long userId = (Long) authentication.getPrincipal();

		List<OrderItemsDTO> orderItemsList = ordersService.getOrderItems(userId);
		return new ResponseEntity<>(orderItemsList, HttpStatus.OK);

	}

}