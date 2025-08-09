package com.kruthik.dtos;

import com.kruthik.enums.AccountType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDTO {

	@NotBlank(message = "Name is required.")
	@Pattern(regexp = "^[A-Za-z\s]+$", message = "Name cannot contain any special characters or numbers.")
	@Size(min = 3, max = 30, message = "Name cannot have less than 3 characters or more than 30 characters")
	private String name;

	@NotBlank(message = "Email is required.")
	@Pattern(regexp = "^[A-Za-z0-9]+@[A-Za-z]+\\.[A-Za-z]{2,}$", message = "Enter a valid email address.")
	private String email;

	@NotBlank(message = "Password is required.")
	@Size(min = 6, message = "Password must be 6 characters long.")
	private String password;

	@NotNull(message = "Account Type is required.")
	private AccountType accountType; // Recruiter or Job_Seeker

	private String companyName; // for Recruiters

}
