package com.kruthik.util;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.kruthik.dtos.JobRequestDTO;
import com.kruthik.dtos.PublicJobResponseDTO;
import com.kruthik.dtos.RecruiterJobResponseDTO;
import com.kruthik.entities.Job;

@Mapper(componentModel = "spring")
public interface JobMapper {

	Job dtoToEntity(JobRequestDTO dto);

	RecruiterJobResponseDTO entityToRecruiterDto(Job entity);

	@Mapping(source = "user", target = "recruiter")
	PublicJobResponseDTO entityToPublicDto(Job entity);

}
