package com.cts.processPension.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
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
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

/**
 * Global Exception Handler to handle all the exception within the application
 * 
 * @author Shubham Nawani
 *
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	@Autowired
	private ObjectMapper objectMapper;

	@PostConstruct
	public void setUp() {
		objectMapper.registerModule(new JavaTimeModule());
	}

	/**
	 * Handles input validation errors
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		ErrorResponse response = new ErrorResponse();
		response.setMessage("Invalid Credentials");
		response.setTimestamp(LocalDateTime.now());

		// Get all validation errors
		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());

		// Add errors to the response map
		response.setFieldErrors(errors);

		return new ResponseEntity<>(response, headers, status);
	}

	/**
	 * This will handle all Feign related Exception
	 * 
	 * @param exception
	 * @param response
	 * @return ErrorResponse
	 * @throws JsonProcessingException
	 * @throws JsonMappingException
	 */
	@ExceptionHandler(FeignException.class)
	public ResponseEntity<ErrorResponse> handleFeignStatusException(FeignException exception,
			HttpServletResponse response) throws JsonProcessingException {
		log.debug("Handling Feign Client");
		ErrorResponse errorResponse = objectMapper.readValue(exception.contentUTF8(), ErrorResponse.class);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	/**
	 * This method will handle NotFoundException
	 * 
	 * @param exception
	 * @param response
	 * @return ErrorResponse
	 */
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException exception,
			HttpServletResponse response) {
		log.debug("Handling Aadhaar Number not found exception");
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setMessage(exception.getMessage());
		errorResponse.setTimestamp(LocalDateTime.now());
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
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setMessage(exception.getMessage());
		errorResponse.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
	}

}
