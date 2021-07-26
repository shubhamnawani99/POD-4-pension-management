package com.cts.pensionerDetails.Exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ErrorResponse {
	

	private String message;
	private LocalDateTime timestamp;
	private HttpStatus status;

}
