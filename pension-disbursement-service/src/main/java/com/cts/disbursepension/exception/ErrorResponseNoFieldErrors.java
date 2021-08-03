package com.cts.disbursepension.exception;

import java.time.LocalDateTime;

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
public class ErrorResponseNoFieldErrors {
	private LocalDateTime timestamp;
	private String message;
	
	
	public ErrorResponseNoFieldErrors(String message) {
		this.timestamp = LocalDateTime.now();
		this.message = message;
	}

}
