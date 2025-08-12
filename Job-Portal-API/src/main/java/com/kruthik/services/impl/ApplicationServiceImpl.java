package com.kruthik.services.impl;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kruthik.dtos.JobSeekerApplicationResponseDTO;
import com.kruthik.entities.Application;
import com.kruthik.entities.Job;
import com.kruthik.entities.User;
import com.kruthik.enums.ApplicationStatus;
import com.kruthik.exceptions.ApplicationNotFoundException;
import com.kruthik.exceptions.JobNotFoundException;
import com.kruthik.repositories.ApplicationRepository;
import com.kruthik.repositories.JobRepository;
import com.kruthik.repositories.UserRepository;
import com.kruthik.services.ApplicationService;
import com.kruthik.util.ApplicationMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

	private final UserRepository userRepository;
	private final JobRepository jobRepository;
	private final ApplicationRepository applicationRepository;
	private final ApplicationMapper applicationMapper;

	@Override
	public Integer applyForJob(Authentication authentication, int jobId) {

		String email = authentication.getName();
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));

		Job job = jobRepository.findById(jobId)
				.orElseThrow(() -> new JobNotFoundException("Job Not Found with the Id: " + jobId));

		Application application = Application.builder().user(user).job(job).resume(user.getResumePath())
				.status(ApplicationStatus.PENDING).appliedDate(LocalDate.now()).build();

		return applicationRepository.save(application).getId();
	}

	@Override
	public JobSeekerApplicationResponseDTO findApplicationByIdAndUser(Authentication authentication, int applicationId) {

		User user = userRepository.findByEmail(authentication.getName()).orElseThrow(
				() -> new UsernameNotFoundException("User Not Found with email: " + authentication.getName()));

		Application application = applicationRepository.findByIdAndUser(applicationId, user)
				.orElseThrow(() -> new ApplicationNotFoundException(
						"You don\'t have any application with the Id: " + applicationId));

		return applicationMapper.entityToDto(application);
	}

	@Override
	public Page<JobSeekerApplicationResponseDTO> findAllApplicationsByUser(Authentication authentication,
			int pageNumber, int pageSize, String sortBy, boolean descending) {

		User user = userRepository.findByEmail(authentication.getName()).orElseThrow(
				() -> new UsernameNotFoundException("User Not Found with email: " + authentication.getName()));

		Direction direction = descending ? Direction.DESC : Direction.ASC;
		Sort sort = Sort.by(direction, sortBy);
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		Page<Application> allApplicationsByUser = applicationRepository.findAllByUser(pageable, user);

		return allApplicationsByUser.map(applicationMapper::entityToDto);
	}

}
