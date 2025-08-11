package com.kruthik.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobRequestDTO {

	@NotBlank(message = "Name is required.")
	@Size(min = 3, max = 30, message = "Name cannot have less than 3 characters or more than 30 characters")
	private String title;

	@NotBlank(message = "Description is required.")
	private String description;

	@NotBlank(message = "Location is required.")
	@Size(min = 3, max = 50, message = "Location cannot have less than 3 characters or more than 50 characters")
	private String location;

	@NotBlank(message = "Salary is required.")
	private double salary;

}
