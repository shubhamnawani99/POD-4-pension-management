package com.cts.pensionerDetails.Exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Sreekanth Gantela
 * 
 * Class for sending a custom error response in exception handler
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {

	private String message;
	private LocalDateTime timestamp;
	private HttpStatus status;

}
