package com.kruthik.dtos;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorDTO {

	private String message;
	private HttpStatus httpStatus;
	private Integer statusCode;
	private LocalDateTime timestamp;

}
