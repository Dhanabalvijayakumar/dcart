package com.dhanabal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dhanabal.dto.LoginDTO;
import com.dhanabal.dto.LoginResponseDTO;
import com.dhanabal.dto.UsersDTO;
import com.dhanabal.entity.TokenBlacklist;
import com.dhanabal.exception.EcommerceException;
import com.dhanabal.repository.TokenBlacklistRepository;
import com.dhanabal.service.UserServiceImpl;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "/api/auth")
public class UsersController {

	@Autowired
	private UserServiceImpl usersService;

	@Autowired
	private TokenBlacklistRepository tokenBlocklistRepo;

	@PostMapping(value = "/user/register")
	public ResponseEntity<String> registerUser(@Valid @RequestBody UsersDTO dto) throws EcommerceException {

		Long userId = usersService.registerUser(dto);
		String successMsg = "Successfully added user with user ID: " + userId;
		return new ResponseEntity<>(successMsg, HttpStatus.CREATED);

	}

	@GetMapping(value = "/user/checkEmail")
	public ResponseEntity<Boolean> verifyEmailNotPresent(@RequestParam("email") String email)
			throws EcommerceException {

		boolean successMsg = usersService.verifyEmailNotPresent(email);
		return new ResponseEntity<>(successMsg, HttpStatus.OK);

	}

	@PostMapping(value = "/user/login")
	public ResponseEntity<LoginResponseDTO> loginUser(@RequestBody LoginDTO dto) throws EcommerceException {

		LoginResponseDTO loginResponseDTO = usersService.loginUser(dto);
		return new ResponseEntity<>(loginResponseDTO, HttpStatus.OK);

	}

	@PostMapping(value = "/user/logout")
	public ResponseEntity<String> logout(@RequestHeader("Authorization") String header) {

		String token = header.substring(7);

		TokenBlacklist blacklist = new TokenBlacklist();
		blacklist.setToken(token);
		tokenBlocklistRepo.save(blacklist);

		String msg = "Logged out successfully";
		return new ResponseEntity<>(msg, HttpStatus.CREATED);

	}

}