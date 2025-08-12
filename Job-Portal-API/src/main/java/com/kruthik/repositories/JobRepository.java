package com.kruthik.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kruthik.entities.Job;
import com.kruthik.entities.User;

public interface JobRepository extends JpaRepository<Job, Integer> {

	Optional<Job> findByIdAndIsExpiredFalse(int jobId);

	Page<Job> findAllByIsExpiredFalse(Pageable pageable);

	Page<Job> findAllByUser(User recruiter, Pageable pageable);
	
	Optional<Job> findByIdAndUser(int jobId, User user);

}
