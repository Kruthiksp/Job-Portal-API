package com.kruthik.services.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kruthik.dtos.JobRequestDTO;
import com.kruthik.dtos.PublicJobResponseDTO;
import com.kruthik.dtos.RecruiterJobResponseDTO;
import com.kruthik.entities.Job;
import com.kruthik.entities.User;
import com.kruthik.exceptions.JobNotFoundException;
import com.kruthik.repositories.JobRepository;
import com.kruthik.repositories.UserRepository;
import com.kruthik.services.JobService;
import com.kruthik.util.JobMapper;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

	private final JobMapper jobMapper;
	private final JobRepository jobRepository;
	private final UserRepository userRepository;

	@Override
	public Job postJob(JobRequestDTO jobRequestDTO, Authentication authentication) {

		String recruiterEmail = authentication.getName();

		if (userRepository.existsByEmail(recruiterEmail)) {

			/* Find the Recruiter */
			User recruiter = userRepository.findByEmail(recruiterEmail)
					.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + recruiterEmail));

			/* Convert DTO to Entity */
			Job entity = jobMapper.dtoToEntity(jobRequestDTO);

			/* set Current Date and RecruiterId */
			entity.setCreateDate(LocalDate.now());
			entity.setUser(recruiter);

			return jobRepository.save(entity);

		} else {
			throw new UsernameNotFoundException("User not found with email: " + recruiterEmail);
		}

	}

	@Override
	public PublicJobResponseDTO findJobById(int jobId) {

		Job byId = jobRepository.findByIdAndIsExpiredFalse(jobId)
				.orElseThrow(() -> new JobNotFoundException("Job not found with the Id:" + jobId));

		return jobMapper.entityToPublicDto(byId);
	}

	@Override
	public Page<PublicJobResponseDTO> findAllJobs(int pageNumber, int pageSize, String sortBy, boolean descending) {

		Direction direction = descending ? Direction.DESC : Direction.ASC;
		Sort sort = Sort.by(direction, sortBy);
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		Page<Job> allJobsEntity = jobRepository.findAllByIsExpiredFalse(pageable);

		return allJobsEntity.map(jobMapper::entityToPublicDto);
	}

	@Override
	public Page<RecruiterJobResponseDTO> findAllJobsByRecruiter(Authentication authentication, int pageNumber,
			int pageSize, String sortBy, boolean descending) {

		User recruiter = userRepository.findByEmail(authentication.getName()).orElseThrow();

		Direction direction = descending ? Direction.DESC : Direction.ASC;
		Sort sort = Sort.by(direction, sortBy);
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		Page<Job> allJobsEntity = jobRepository.findAllByUser(recruiter, pageable);

		return allJobsEntity.map(jobMapper::entityToRecruiterDto);
	}

	@Override
	@Transactional
	public void updateJob(int jobId, JobRequestDTO jobRequestDTO, Authentication authentication) {
		User user = userRepository.findByEmail(authentication.getName()).orElseThrow(
				() -> new UsernameNotFoundException("User not found with email: " + authentication.getName()));

		Job job = jobRepository.findByIdAndUser(jobId, user)
				.orElseThrow(() -> new JobNotFoundException("You don't own a Job with the Id:" + jobId));

		job.setTitle(jobRequestDTO.getTitle());
		job.setDescription(jobRequestDTO.getDescription());
		job.setLocation(jobRequestDTO.getLocation());
		job.setSalary(jobRequestDTO.getSalary());
		job.setLastDateToApply(jobRequestDTO.getLastDateToApply());

	}

	@Override
	@Transactional
	public void deleteJob(int jobId, Authentication authentication) {
		User user = userRepository.findByEmail(authentication.getName()).orElseThrow(
				() -> new UsernameNotFoundException("User not found with email: " + authentication.getName()));

		Job job = jobRepository.findByIdAndUser(jobId, user)
				.orElseThrow(() -> new JobNotFoundException("You don't own a Job with the Id:" + jobId));

		if (job != null) {
			job.setExpired(true);
		}
	}

	@Override
	@Scheduled(cron = "0 0 0 * * ?")
	@Transactional
	public void deleteExpiredJobs() {
		List<Job> expiredJobs = jobRepository.findAllByIsExpiredFalseAndLastDateToApplyBefore(LocalDate.now());
		expiredJobs.stream().forEach(job -> job.setExpired(true));
	}
	
	@PostConstruct
	@Transactional
	public void deleteExpireJobsAtStartup() {
	    deleteExpiredJobs();
	}

}
