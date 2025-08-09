package com.kruthik.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kruthik.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	boolean existsByEmail(String email);
}
