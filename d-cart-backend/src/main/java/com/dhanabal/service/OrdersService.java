package com.dhanabal.service;

import java.util.List;

import com.dhanabal.dto.OrderItemsDTO;
import com.dhanabal.exception.EcommerceException;

public interface OrdersService {

	List<OrderItemsDTO> getOrderItems(Long userId) throws EcommerceException;
	
}
