package com.kruthik.dtos;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PublicJobResponseDTO {

	private int id;
	private String title;
	private String description;
	private String location;
	private double salary;
	private LocalDate createDate;

	private RecruiterSummaryDTO recruiter;
}
