package com.dhanabal.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dhanabal.config.JwtUtil;
import com.dhanabal.dto.LoginDTO;
import com.dhanabal.dto.LoginResponseDTO;
import com.dhanabal.dto.UsersDTO;
import com.dhanabal.entity.Users;
import com.dhanabal.exception.EcommerceException;
import com.dhanabal.repository.UsersRepository;

import jakarta.transaction.Transactional;

@Service(value = "usersService")
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UsersRepository usersRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private EmailService emailService;

	@Override
	public Long registerUser(UsersDTO dto) throws EcommerceException {

		if (usersRepo.findUserByEmail(dto.getEmail()).isPresent()) {
			throw new EcommerceException("Email already registered");
		}

		if (usersRepo.findUserByMobile(dto.getMobile()).isPresent()) {
			throw new EcommerceException("Mobile number already registered");
		}

//		Users newUser = new Users();
//		newUser.setName(dto.getName());
//		newUser.setEmail(dto.getEmail());
//		newUser.setMobile(dto.getMobile());
//		newUser.setRole(dto.getRole());
//		newUser.setPassword(dto.getPassword());
//		newUser.setCreatedTime(dto.getCreatedTime());

		Users newUser = modelMapper.map(dto, Users.class);
		newUser.setPassword(passwordEncoder.encode(dto.getPassword()));

		Users savedUser = usersRepo.save(newUser);

//		emailService.sendRegistrationMail(savedUser.getEmail(), savedUser.getName(), savedUser.getMobile(),
//				dto.getPassword());

		return newUser.getUserId();

	}

	@Override
	public boolean verifyEmailNotPresent(String email) throws EcommerceException {

		if (usersRepo.findUserByEmail(email).isPresent()) {
			throw new EcommerceException("Email already registered");
		}

//		return "Verified successfully";
		return true;
	}

	@Override
	public LoginResponseDTO loginUser(LoginDTO dto) throws EcommerceException {

		if (dto.getMobile() == null) {

			Users user = usersRepo.findUserByEmail(dto.getEmail())
					.orElseThrow(() -> new EcommerceException("User not found"));

			if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
				throw new EcommerceException("Password not correct");
			} else {
				String token = jwtUtil.generateToken(user.getUserId());

//				String token = jwtUtil.generateToken(user.getEmail());

				LoginResponseDTO loginResponseDTO = modelMapper.map(user, LoginResponseDTO.class);
				loginResponseDTO.setToken(token);

				return loginResponseDTO;
			}

		}

		Users user = usersRepo.findUserByMobile(dto.getMobile())
				.orElseThrow(() -> new EcommerceException("User not found"));

		if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
			throw new EcommerceException("Password not correct");
		} else {
			String token = jwtUtil.generateToken(user.getUserId());

//			String token = jwtUtil.generateToken(user.getEmail());

			LoginResponseDTO loginResponseDTO = modelMapper.map(user, LoginResponseDTO.class);
			loginResponseDTO.setToken(token);

			return loginResponseDTO;
		}

	}

}
