package com.kruthik.dtos;

import com.kruthik.enums.Roles;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {

	private int id;
	private String name;
	private String email;
	private Roles role;
	private String companyName;
	private String resumePath;
	
}
