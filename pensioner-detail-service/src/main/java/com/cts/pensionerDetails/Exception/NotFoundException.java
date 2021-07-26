package com.cts.pensionerDetails.Exception;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;



/**
 * @author SREEKANTH GANTELA
 * 
 * Class to handle NotFoundException if Aadhar number is not found
 *
 */

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends Exception{

	private static final long serialVersionUID = 1L;

	public NotFoundException(String msg) {
		super(msg);
	}
}
