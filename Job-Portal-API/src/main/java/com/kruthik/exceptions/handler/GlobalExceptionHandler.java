package com.kruthik.exceptions.handler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kruthik.dtos.ErrorDTO;
import com.kruthik.exceptions.CompanyNameRequiredException;
import com.kruthik.exceptions.UserAlreadyExistException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * This method handles the validation and displays the error messages
	 * accordingly
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException e) {

		Map<String, String> errors = new LinkedHashMap<>();
		errors.put("timestamp", LocalDateTime.now().toString());
		e.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}

	/**
	 * This method is executed Enum validation errors.
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorDTO> handleInvalidEnum(HttpMessageNotReadableException e) {

		ErrorDTO error = ErrorDTO.builder().message(e.getMostSpecificCause().getMessage())
				.httpStatus(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value())
				.timestamp(LocalDateTime.now()).build();

		return ResponseEntity.status(error.getStatusCode()).body(error);
	}

	/**
	 * This method is executed when the same emailId is used for registration again.
	 */
	@ExceptionHandler(UserAlreadyExistException.class)
	public ResponseEntity<ErrorDTO> handleUserAlreadyExistException(UserAlreadyExistException e) {

		ErrorDTO error = ErrorDTO.builder().message(e.getMessage()).httpStatus(HttpStatus.CONFLICT)
				.statusCode(HttpStatus.CONFLICT.value()).timestamp(LocalDateTime.now()).build();

		return ResponseEntity.status(error.getStatusCode()).body(error);
	}

	/**
	 * This method is executed when the recruiter don't fill the company name.
	 */
	@ExceptionHandler(CompanyNameRequiredException.class)
	public ResponseEntity<ErrorDTO> handleCompanyNameRequiredException(CompanyNameRequiredException e) {

		ErrorDTO error = ErrorDTO.builder().message(e.getMessage()).httpStatus(HttpStatus.BAD_REQUEST)
				.statusCode(HttpStatus.BAD_REQUEST.value()).timestamp(LocalDateTime.now()).build();

		return ResponseEntity.status(error.getStatusCode()).body(error);
	}
}
