package com.kruthik.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kruthik.entities.Job;
import com.kruthik.entities.User;

public interface JobRepository extends JpaRepository<Job, Integer> {

	Page<Job> findAllByUser(User recruiter, Pageable pageable);

}
