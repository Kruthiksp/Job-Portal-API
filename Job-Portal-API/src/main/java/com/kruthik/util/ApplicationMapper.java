package com.kruthik.util;

import org.mapstruct.Mapper;

import com.kruthik.dtos.JobSeekerApplicationResponseDTO;
import com.kruthik.entities.Application;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {

	JobSeekerApplicationResponseDTO entityToDto(Application entity);

}
