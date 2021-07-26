package com.cts.disbursepension.exception;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * This method handles data with invalid arguments
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.debug("Handling Argument not valid exception");
		return new ResponseEntity<>(new ErrorResponse("Invalid Input"), status);
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
		return new ResponseEntity<>(new ErrorResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
	}

	/**
	 * This method will handle InvalidTokenException
	 * @param exception
	 * @param response
	 * @return ErrorResponse
	 */
	@ExceptionHandler(InvalidTokenException.class)
	public ResponseEntity<ErrorResponse> handleInvalidTokenException(InvalidTokenException exception,
			HttpServletResponse response) {
		log.debug("Handling Invalid Token exception");
		return new ResponseEntity<>(new ErrorResponse(exception.getMessage()), HttpStatus.FORBIDDEN);
	}

}
