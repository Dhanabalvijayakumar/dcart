package com.dhanabal.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {

	private String token;

	private Long userId;

	private String name;

	private String email;

	private String mobile;

	private String password;

	private Role role;

	private LocalDateTime createdTime;

}