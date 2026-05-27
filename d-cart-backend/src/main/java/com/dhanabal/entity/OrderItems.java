package com.dhanabal.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "order_items")
public class OrderItems {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_item_id")
	private Long orderItemId;

	@Column(name = "quantity")
	private int quantity;

	@Column(name = "price")
	private BigDecimal price;

	@ManyToOne
	@JoinColumn(name = "order_id")
	private Orders orders;

//	@OneToMany(cascade = CascadeType.ALL)
//	@JoinColumn(name = "product_id")
//	private List<Products> productsList;	
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Products product;

}