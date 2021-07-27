package com.cts.pensionerDetails.Exception;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;



/**
 * @author SREEKANTH GANTELA
 * 
 * Class to handle NotFoundException if Aadhaar number is not found
 *
 */

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public NotFoundException(String msg) {
		super(msg);
	}
}
