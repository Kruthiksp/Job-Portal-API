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
public class RecruiterJobResponseDTO {

	private int id;
	private String title;
	private String description;
	private String location;
	private double salary;
	private LocalDate createDate;
	private LocalDate lastDateToApply;

//	private RecruiterSummaryDTO recruiter;
	private List<RecruiterApplicationResponseDTO> applications;
}
