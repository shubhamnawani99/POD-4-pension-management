package com.cts.authorization.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Handles all the global exceptions
 * 
 * @author Shubham Nawani
 *
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * Handles invalid credentials exception
	 * 
	 * @param invalidCredentialsException
	 * @return Response Entity of custom type Error Response with error message and
	 *         time-stamp
	 */
	@ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<ErrorResponse> handlesUserNotFoundException(
			InvalidCredentialsException invalidCredentialsException) {

		ErrorResponse response = new ErrorResponse(invalidCredentialsException.getMessage(), LocalDateTime.now());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
}
