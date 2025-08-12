package com.kruthik.dtos;

import java.time.LocalDate;

import com.kruthik.enums.ApplicationStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecruiterApplicationResponseDTO {
	
	private int id;
	private String resume;
	private ApplicationStatus status;
	private LocalDate appliedDate;
	private UserResponseDTO applicant; // visible only Recruiters
}
