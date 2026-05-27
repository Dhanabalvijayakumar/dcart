package com.dhanabal.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsersDTO {

	private Long userId;

	@NotBlank(message = "Name is required")
	@Pattern(regexp = "^[A-Z][a-z]+( [A-Z][a-z]+)+$", message = "Invalid name format")
	private String name;

	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email address")
	private String email;

	@NotBlank(message = "Mobile number is required")
	@Pattern(regexp = "^^[0-9]{10}$$", message = "Mobile number must be 10 digits")
	private String mobile;

	@NotBlank(message = "Password is required")
	@Size(min = 6, message = "Password must be atleast 6 characters")
	private String password;

	@NotNull(message = "Role is required")
	private Role role;

	private LocalDateTime createdTime;

}