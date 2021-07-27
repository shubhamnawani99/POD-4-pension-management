package com.cts.processPension.exception;

/**
 * This exception is thrown when user sends invalid token in api request
 * @author Chop
 *
 */
public class InvalidTokenException extends RuntimeException {
	private static final long serialVersionUID = -5091342239524021914L;

	public InvalidTokenException(String message) {
		super(message);
	}

}
