package com.cts.pensionerDetails.Exception;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Class for customizing error response in exception handler
 * 
 * @author Sreekanth Gantela
 *
 */
@Getter
@AllArgsConstructor
public class ErrorResponse {

	private String message;
	private LocalDateTime timestamp;

	/**
	 * Used only for input validation errors
	 */
	@JsonInclude(Include.NON_NULL)
	private List<String> fieldErrors;
}
