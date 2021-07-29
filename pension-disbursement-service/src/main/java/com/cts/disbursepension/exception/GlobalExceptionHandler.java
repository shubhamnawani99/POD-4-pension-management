
package com.cts.disbursepension.exception;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.Feign;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

/**
 * This Class will handle all major exception thrown in the application
 * 
 * @author Anas Zubair
 *
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * This method handles data with invalid arguments
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.debug("Handling Argument not valid exception");
		return new ResponseEntity<>(new ErrorResponse("Invalid Input"), headers, status);
	}

	/**
	 * This will handle all Feign related Exception
	 * 
	 * @param exception
	 * @param response
	 * @return ErrorResponse
	 */
	@ExceptionHandler(FeignException.class)
	public ResponseEntity<ErrorResponse> handleFeignStatusException(FeignException exception,
			HttpServletResponse response) {
		log.debug("Handling Feign Client");
		log.debug("Message: {}", exception.getMessage());
		ErrorResponse errorResponse;
		log.debug("UTF-8 Message: {}", exception.contentUTF8());
		if (exception.contentUTF8().isBlank()) {
			errorResponse = new ErrorResponse("Invalid Request");
		} else {
			try {
				log.debug("Trying...");
				errorResponse = objectMapper.readValue(exception.contentUTF8(), ErrorResponse.class);
			} catch (JsonProcessingException e) {
				errorResponse = new ErrorResponse(exception.getCause().getMessage());
				log.error("Processing Error {}", e.toString());
			}
		}
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	/**
	 * This method will handle InvalidTokenException
	 * 
	 * @param exception
	 * @param response
	 * @return ErrorResponse
	 */
	@ExceptionHandler(InvalidTokenException.class)
	public ResponseEntity<ErrorResponse> handleInvalidTokenException(InvalidTokenException exception,
			HttpServletResponse response) {
		log.debug("Handling Invalid Token exception");
		log.debug("Message: {}", exception.getMessage());
		return new ResponseEntity<>(new ErrorResponse(exception.getMessage()), HttpStatus.FORBIDDEN);
	}

}
