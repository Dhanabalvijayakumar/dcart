package com.dhanabal.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.dhanabal.dto.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long userId;

	@Column(name = "name")
	private String name;

	@Column(name = "email", unique = true)
	private String email;
	
	@Column(name = "mobile", unique = true)
	private String mobile;

	@Column(name = "password")
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(name = "role")
	private Role role;

	@Column(name = "created_time")
	@CreationTimestamp
	private LocalDateTime createdTime;

}