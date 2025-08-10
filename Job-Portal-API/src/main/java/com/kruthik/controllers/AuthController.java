package com.kruthik.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kruthik.dtos.AuthRequestDTO;
import com.kruthik.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/authenticate")
@RequiredArgsConstructor
public class AuthController {

	private final JwtUtil jwtUtil;

	@PostMapping
	public String generateToken(@RequestBody AuthRequestDTO authRequestDTO) {
		return jwtUtil.generateToken(authRequestDTO.getEmail());
	}

}
