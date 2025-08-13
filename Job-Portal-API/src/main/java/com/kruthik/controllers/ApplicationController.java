package com.kruthik.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kruthik.dtos.JobSeekerApplicationResponseDTO;
import com.kruthik.services.ApplicationService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/job")
@RequiredArgsConstructor
@Tag(
	    name = "Job Applications",
	    description = "Endpoints for job seekers to apply for jobs, view their applications, and recruiters to view applications for their posted jobs."
	)
public class ApplicationController {

	private final ApplicationService applicationService;

	@PostMapping("/{jobId}/apply")
	@PreAuthorize("hasRole('JOB_SEEKER')")
	public ResponseEntity<String> applyJob(@PathVariable int jobId, Authentication authentication) {

		Integer applicationId = applicationService.applyForJob(authentication, jobId);

		return ResponseEntity.status(HttpStatus.OK)
				.body("Applied Successfully\nYour Application Number is: " + applicationId);
	}

	@GetMapping("/application/{applicationId}")
	@PreAuthorize("hasRole('JOB_SEEKER')")
	public ResponseEntity<JobSeekerApplicationResponseDTO> getApplicationById(@PathVariable int applicationId,
			Authentication authentication) {
		JobSeekerApplicationResponseDTO application = applicationService.findApplicationByIdAndUser(authentication,
				applicationId);
		return ResponseEntity.status(HttpStatus.OK).body(application);
	}

	@GetMapping("/all-my-applications")
	@PreAuthorize("hasRole('JOB_SEEKER')")
	public ResponseEntity<Page<JobSeekerApplicationResponseDTO>> getApplicationByUser(Authentication authentication,
			@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "5") int pageSize,
			@RequestParam(defaultValue = "appliedDate") String sortBy,
			@RequestParam(defaultValue = "true") boolean descending) {

		Page<JobSeekerApplicationResponseDTO> allJobsByRecruiter = applicationService
				.findAllApplicationsByUser(authentication, pageNumber, pageSize, sortBy, descending);

		return ResponseEntity.status(HttpStatus.OK).body(allJobsByRecruiter);

	}

}
