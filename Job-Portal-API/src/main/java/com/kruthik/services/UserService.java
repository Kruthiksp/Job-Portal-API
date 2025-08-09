package com.kruthik.services;

import com.kruthik.dtos.UserRequestDTO;
import com.kruthik.entities.User;
import com.kruthik.exceptions.CompanyNameRequiredException;
import com.kruthik.exceptions.UserAlreadyExistException;

public interface UserService {

	User saveUser(UserRequestDTO userDTO) throws UserAlreadyExistException, CompanyNameRequiredException;

}
