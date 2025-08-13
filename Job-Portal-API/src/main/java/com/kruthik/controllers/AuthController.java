package com.kruthik.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kruthik.dtos.AuthRequestDTO;
import com.kruthik.util.JwtUtil;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/authenticate")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user login, logout, and token management using JWT-based authentication.")
public class AuthController {

	private final JwtUtil jwtUtil;
	private final PasswordEncoder passwordEncoder;
	private final UserDetailsService userDetailsService;

	@PostMapping
	public ResponseEntity<String> generateToken(@RequestBody AuthRequestDTO authRequestDTO) {

		String token = null;

		UserDetails user = userDetailsService.loadUserByUsername(authRequestDTO.getEmail());
		boolean matches = passwordEncoder.matches(authRequestDTO.getPassword(), user.getPassword());

		if (matches) {
			token = jwtUtil.generateToken(authRequestDTO.getEmail());
		} else {
			throw new RuntimeException("Invalid Username or Password.");
		}

		return ResponseEntity.status(HttpStatus.OK).body(token);
	}

}
