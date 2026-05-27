package com.dhanabal.service;

import com.dhanabal.dto.LoginDTO;
import com.dhanabal.dto.LoginResponseDTO;
import com.dhanabal.dto.UsersDTO;
import com.dhanabal.exception.EcommerceException;

public interface UserService {

	Long registerUser(UsersDTO dto) throws EcommerceException;

	boolean verifyEmailNotPresent(String email) throws EcommerceException;

	LoginResponseDTO loginUser(LoginDTO dto) throws EcommerceException;

}
