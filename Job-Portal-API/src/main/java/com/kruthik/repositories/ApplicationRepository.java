package com.kruthik.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kruthik.entities.Application;
import com.kruthik.entities.User;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {

	Optional<Application> findByIdAndUser(int id, User user);

	Page<Application> findAllByUser(Pageable pageable, User user);
	
}
