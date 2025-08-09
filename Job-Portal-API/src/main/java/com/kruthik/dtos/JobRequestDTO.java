package com.kruthik.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobRequestDTO {

	private String title;
	private String description;
	private String location;
	private double salary;
	
}
