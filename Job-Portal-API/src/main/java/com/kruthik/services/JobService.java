package com.kruthik.services;

import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

import com.kruthik.dtos.JobRequestDTO;
import com.kruthik.dtos.PublicJobResponseDTO;
import com.kruthik.dtos.RecruiterJobResponseDTO;
import com.kruthik.entities.Job;

public interface JobService {

	Job postJob(JobRequestDTO jobRequestDTO, Authentication authentication);

	PublicJobResponseDTO findJobById(int jobId);

	Page<PublicJobResponseDTO> findAllJobs(int pageNumber, int pageSize, String sortBy, boolean descending);

	Page<RecruiterJobResponseDTO> findAllJobsByRecruiter(Authentication authentication, int pageNumber, int pageSize,
			String sortBy, boolean descending);

	void updateJob(int jobId, JobRequestDTO jobRequestDTO, Authentication authentication);

	void deleteJob(int jobId, Authentication authentication);

}
