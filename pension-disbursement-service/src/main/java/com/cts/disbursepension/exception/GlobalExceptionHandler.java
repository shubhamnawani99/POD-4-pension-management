
package com.cts.disbursepension.exception;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

		log.debug("Handling Argument not valid exception in Disburse Pension Microservice");
		// Get all validation errors
		List<String> errors = exception.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage)
				.collect(Collectors.toList());
		ErrorResponse errorResponse = new ErrorResponse("Invalid Credentials", errors);
		return new ResponseEntity<>(errorResponse, headers, status);
	}

	/**
	 * This will handle all Feign related Exception
	 * 
	 * @author Shubham Nawani, Anas Zubair
	 * @param exception
	 * @param response
	 * @return ErrorResponse
	 */
	@ExceptionHandler(FeignException.class)
	public ResponseEntity<ErrorResponseNoFieldErrors> handleFeignStatusException(FeignException exception,
			HttpServletResponse response) {
		log.debug("Handling Feign Client");
		log.debug("Message: {}", exception.getMessage());
		ErrorResponseNoFieldErrors errorResponse;
		log.debug("UTF-8 Message: {}", exception.contentUTF8());
		if (exception.contentUTF8().isBlank()) {
			//when feignClient request is timeout or service is unavailable
			errorResponse = new ErrorResponseNoFieldErrors("Service is offline");
		} else {
			try {
				//when error response has valid format
				log.debug("Trying...");
				errorResponse = objectMapper.readValue(exception.contentUTF8(), ErrorResponseNoFieldErrors.class);
				log.debug("Successful.. Message is: {}", errorResponse.getMessage());
			} catch (JsonProcessingException e) {
				//Unknown error response
				//Converting raw response to valid format
				errorResponse = new ErrorResponseNoFieldErrors(exception.contentUTF8());
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
