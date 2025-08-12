package com.kruthik.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kruthik.dtos.UserRequestDTO;
import com.kruthik.entities.User;
import com.kruthik.services.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping("/registration")
	public ResponseEntity<String> saveUser(@Valid @ModelAttribute UserRequestDTO userRequestDTO) {
		User saveUser = userService.saveUser(userRequestDTO);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body("Account Created Successfully\nYour User Id: " + saveUser.getId());
	}

	@GetMapping
	public String test() {
		return "login";
	}
	
}
