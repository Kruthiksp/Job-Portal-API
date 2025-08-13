package com.kruthik.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kruthik.dtos.JobRequestDTO;
import com.kruthik.dtos.PublicJobResponseDTO;
import com.kruthik.dtos.RecruiterJobResponseDTO;
import com.kruthik.entities.Job;
import com.kruthik.services.JobService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/job")
@RequiredArgsConstructor
@Tag(
	    name = "Job Management",
	    description = "Endpoints to post, edit, delete, and fetch jobs. Public endpoints for viewing jobs and recruiter-specific endpoints for managing posted jobs."
	)
public class JobController {

	private final JobService jobService;

	@PostMapping("/post")
	@PreAuthorize("hasRole('RECRUITER')")
	public ResponseEntity<String> postJob(@RequestBody JobRequestDTO jobRequestDTO, Authentication authentication) {

		Job job = jobService.postJob(jobRequestDTO, authentication);
		return ResponseEntity.status(HttpStatus.CREATED).body("Job Posted Successfully.\nJob Id is: " + job.getId());
	}

	@GetMapping("/{jobId}")
	public ResponseEntity<PublicJobResponseDTO> getJobById(@PathVariable int jobId) {
		PublicJobResponseDTO jobById = jobService.findJobById(jobId);
		return ResponseEntity.status(HttpStatus.OK).body(jobById);
	}

	@GetMapping("/my-jobs")
	@PreAuthorize("hasRole('RECRUITER')")
	public ResponseEntity<Page<RecruiterJobResponseDTO>> getAllJobsBasedOnRecruiter(Authentication authentication,
			@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "5") int pageSize,
			@RequestParam(defaultValue = "createDate") String sortBy,
			@RequestParam(defaultValue = "true") boolean descending) {

		Page<RecruiterJobResponseDTO> allJobs = jobService.findAllJobsByRecruiter(authentication, pageNumber, pageSize,
				sortBy, descending);

		return ResponseEntity.status(HttpStatus.OK).body(allJobs);
	}

	@GetMapping("/all-jobs")
	public ResponseEntity<Page<PublicJobResponseDTO>> getAllJobs(@RequestParam(defaultValue = "0") int pageNumber,
			@RequestParam(defaultValue = "5") int pageSize, @RequestParam(defaultValue = "createDate") String sortBy,
			@RequestParam(defaultValue = "true") boolean descending) {
		Page<PublicJobResponseDTO> allJobs = jobService.findAllJobs(pageNumber, pageSize, sortBy, descending);
		return ResponseEntity.status(HttpStatus.OK).body(allJobs);
	}

	@PutMapping("/update/{jobId}")
	@PreAuthorize("hasRole('RECRUITER')")
	public ResponseEntity<Void> updateJob(@PathVariable int jobId, @RequestBody JobRequestDTO jobRequestDTO,
			Authentication authentication) {
		jobService.updateJob(jobId, jobRequestDTO, authentication);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@DeleteMapping("/delete/{jobId}")
	@PreAuthorize("hasRole('RECRUITER')")
	public ResponseEntity<Void> deleteJob(@PathVariable int jobId,Authentication authentication) {
		jobService.deleteJob(jobId, authentication);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
