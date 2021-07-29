package com.cts.disbursepension.exception;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * This class is used by global exception handler to send error response
 * 
 * @author Anas Zubair
 *
 */
@Getter
@AllArgsConstructor
public class ErrorResponse {
	private LocalDateTime timestamp;
	private String message;

	public ErrorResponse(String message) {
		this.timestamp = LocalDateTime.now();
		this.message = message;
	}
	
	/**
	 * Used only for input validation errors
	 */
	@JsonInclude(Include.NON_NULL)
	private List<String> fieldErrors;

}
