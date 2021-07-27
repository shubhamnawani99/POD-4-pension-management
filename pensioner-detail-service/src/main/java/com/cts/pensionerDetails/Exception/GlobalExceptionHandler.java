package com.cts.pensionerDetails.Exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;



/**
 * 
 * Handles all the global exceptions
 * 
 * @author SREEKANTH GANTELA 
 * 
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
	
	/**
	 * Handles invalid Aadhaar Number exception
	 * 
	 * @param NotFoundException
	 * @return Response Entity of custom type Error Response with error message and
	 *         time-stamp
	 */
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorResponse> handlesUserNotFoundException(
			NotFoundException notFoundException) {

		ErrorResponse response = new ErrorResponse();
		response.setTimestamp(LocalDateTime.now());
		response.setMessage(notFoundException.getMessage());
		response.setStatus(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

}
