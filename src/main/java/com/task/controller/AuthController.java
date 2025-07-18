package com.task.controller;

import com.task.dto.AuthRequestDTO;
import com.task.dto.AuthResponseDTO;
import com.task.model.Role;
import com.task.model.User;
import com.task.repository.UserRepository;
import com.task.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;

	@PostMapping("/register")
	public String register(@RequestBody AuthRequestDTO requestDTO) {

		if (userRepository.existsByUsername(requestDTO.getUsername())) {
			return "Username already exists!";
		}
		Role role = Role.USER;
		if (requestDTO.getRole() != null && requestDTO.getRole().equalsIgnoreCase("ADMIN")) {
			role = Role.ADMIN;
		}

		User newUser = User.builder()
				.username(requestDTO.getUsername())
				.email(requestDTO.getEmail())
				.password(passwordEncoder.encode(requestDTO.getPassword()))
				.role(role)
				.build();

		userRepository.save(newUser);
		return "User registered successfully!";
	}


	@PostMapping("/login")
	public AuthResponseDTO login(@RequestBody AuthRequestDTO requestDTO) {

		User user = userRepository.findByUsername(requestDTO.getUsername())
				.orElseThrow(() -> new RuntimeException("User not found"));

		if (!passwordEncoder.matches(requestDTO.getPassword(), user.getPassword())) {
			throw new RuntimeException("Invalid credentials");
		}

		String token = jwtUtil.generateToken(user);
		return new AuthResponseDTO(token);
	}
}
