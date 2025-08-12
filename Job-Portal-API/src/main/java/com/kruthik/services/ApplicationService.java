package com.kruthik.services;

import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

import com.kruthik.dtos.JobSeekerApplicationResponseDTO;

public interface ApplicationService {

	Integer applyForJob(Authentication authentication, int jobId);
	
	JobSeekerApplicationResponseDTO findApplicationByIdAndUser(Authentication authentication, int applicationId);

	Page<JobSeekerApplicationResponseDTO> findAllApplicationsByUser(Authentication authentication, int pageNumber, int pageSize,
			String sortBy, boolean descending);

}
