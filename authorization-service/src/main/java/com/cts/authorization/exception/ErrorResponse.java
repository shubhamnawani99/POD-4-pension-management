package com.cts.authorization.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Class for sending a custom error response in exception handler
 * 
 * @author Shubham Nawani
 *
 */
@AllArgsConstructor
@Data
public class ErrorResponse {

	private String message;
	private LocalDateTime timestamp;

}
