package com.kruthik.services.impl;

import org.springframework.stereotype.Service;

import com.kruthik.dtos.UserRequestDTO;
import com.kruthik.entities.User;
import com.kruthik.enums.AccountType;
import com.kruthik.enums.Roles;
import com.kruthik.exceptions.CompanyNameRequiredException;
import com.kruthik.exceptions.UserAlreadyExistException;
import com.kruthik.repositories.UserRepository;
import com.kruthik.services.UserService;
import com.kruthik.util.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserMapper userMapper;
	private final UserRepository userRepository;

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
	public User saveUser(UserRequestDTO userDTO) throws UserAlreadyExistException, CompanyNameRequiredException {

		if (userRepository.existsByEmail(userDTO.getEmail())) {
			throw new UserAlreadyExistException("User Already Exist with the Email Id: " + userDTO.getEmail());
		} else {
			User dtoToEntity = userMapper.dtoToEntity(userDTO);

			if (userDTO.getAccountType() == AccountType.Recruiter && userDTO.getCompanyName() != null) {
				dtoToEntity.setRole(Roles.ROLE_RECRUITER);
			} else if (userDTO.getAccountType() == AccountType.Job_Seeker) {
				dtoToEntity.setRole(Roles.ROLE_JOB_SEEKER);
			} else {
				throw new CompanyNameRequiredException("Recruiter must fill the Company Name.");
			}

			return userRepository.save(dtoToEntity);
		}

	}

}
