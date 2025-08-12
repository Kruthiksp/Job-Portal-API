package com.kruthik.services.impl;

import java.time.LocalDate;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kruthik.dtos.UserRequestDTO;
import com.kruthik.entities.User;
import com.kruthik.enums.AccountType;
import com.kruthik.enums.Roles;
import com.kruthik.exceptions.CompanyNameRequiredException;
import com.kruthik.exceptions.ResumeRequiredException;
import com.kruthik.exceptions.UserAlreadyExistException;
import com.kruthik.repositories.UserRepository;
import com.kruthik.services.UserService;
import com.kruthik.util.ResumeUtil;
import com.kruthik.util.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final PasswordEncoder passwordEncoder;
	private final UserMapper userMapper;
	private final UserRepository userRepository;
	private final ResumeUtil resumeUtil;

	/**
	 * 
	 * This method accepts: UserRequestDTO -> maps it with User entity ->saves to
	 * the DB
	 * 
	 * if email already exists it will throw UserAlreadyExistException
	 * 
	 * if the recruiter does not fills the company name it will throw
	 * CompanyNameRequiredException
	 * 
	 */
	@Override
	public User saveUser(UserRequestDTO userDTO) {

		if (userRepository.existsByEmail(userDTO.getEmail())) {
			throw new UserAlreadyExistException("User Already Exist with the Email Id: " + userDTO.getEmail());
		} else {
			User dtoToEntity = userMapper.dtoToEntity(userDTO);
			dtoToEntity.setPassword(passwordEncoder.encode(dtoToEntity.getPassword()));
			dtoToEntity.setCreateDate(LocalDate.now());

			if (userDTO.getAccountType() == AccountType.Recruiter) {
				if (userDTO.getCompanyName() == null || userDTO.getCompanyName().isBlank()) {
					throw new CompanyNameRequiredException("Company name is mandatory for Recruiters");
				}
				dtoToEntity.setRole(Roles.ROLE_RECRUITER);
			} else if (userDTO.getAccountType() == AccountType.Job_Seeker) {
				if (userDTO.getResume() == null || userDTO.getResume().isEmpty()) {
					throw new ResumeRequiredException("Resume is mandatory for Job Seekers");
				}
				if (resumeUtil.isResumeValid(userDTO.getResume())) {
					String resumeURL = resumeUtil.resumeURL(userDTO.getResume(), userDTO.getEmail());
					dtoToEntity.setResumePath(resumeURL);
					dtoToEntity.setRole(Roles.ROLE_JOB_SEEKER);
				} else {
					throw new IllegalArgumentException("Invalid Resume Type.");
				}
			}

			return userRepository.save(dtoToEntity);
		}

	}

}
