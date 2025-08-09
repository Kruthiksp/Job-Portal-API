package com.kruthik.util;

import org.mapstruct.Mapper;

import com.kruthik.dtos.UserRequestDTO;
import com.kruthik.dtos.UserResponseDTO;
import com.kruthik.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

	User dtoToEntity(UserRequestDTO dto);

	UserResponseDTO entityToDto(User entity);
}
