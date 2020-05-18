package app.exceptions;

import org.springframework.http.HttpStatus;

public final class ApiRequestException extends RuntimeException {

	/**
	 *  Constants
	 */
	
	private static final long serialVersionUID = -8772256784904433325L;

	/**
	 * Properties
	 */
	private final HttpStatus httpStatus;

	
	/**
	 * Constructors
	 */
	
	public ApiRequestException(HttpStatus httpStatus, String message) {
		super(message);
		
		this.httpStatus = httpStatus;
	}

	public ApiRequestException(HttpStatus httpStatus, String message, Throwable cause) {
		super(message, cause);
		
		this.httpStatus = httpStatus;
	}
	
	/**
	 * API (Getters)
	 */

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}
