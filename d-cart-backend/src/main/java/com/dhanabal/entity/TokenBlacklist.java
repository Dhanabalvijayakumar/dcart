package com.dhanabal.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "token_blacklist")
public class TokenBlacklist {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long tokenId;

	private String token;

}