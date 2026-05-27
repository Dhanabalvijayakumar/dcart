package com.dhanabal.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class OrdersDTO {

	private Long orderId;
	
	private BigDecimal totalPrice;
	
	private Status status;
	
	private LocalDateTime createdTime;
	
	private UsersDTO usersDTO;
	
}