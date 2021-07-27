package com.cts.disbursepension.exception;

import java.time.LocalDateTime;

import lombok.Getter;

/**
 * This class is used by global exception handler to send error response
 * 
 * @author Anas Zubair
 *
 */
@Getter
public class ErrorResponse {
	private LocalDateTime timestamp;
	private String message;

	public ErrorResponse(String message) {
		this.timestamp = LocalDateTime.now();
		this.message = message;
	}

}
