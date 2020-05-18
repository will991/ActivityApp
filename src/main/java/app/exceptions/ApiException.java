package app.exceptions;

import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;

/**
 *	This class serves as base type for any API exceptions that occur for incoming requests to this application's end points. 
 */
public final class ApiException {

	/**
	 * Properties
	 */
	
	private final String message;
	private final HttpStatus httpStatus;
	private final ZonedDateTime timestamp;
	
	/**
	 * Constructors
	 */
	
	public ApiException(
			final String message, 
			final HttpStatus httpStatus, 
			final ZonedDateTime timestamp) {
		super();
		this.message = message;
		this.httpStatus = httpStatus;
		this.timestamp = timestamp;
	}

	/**
	 * API (Getters)
	 */
	
	public String getMessage() {
		return message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public ZonedDateTime getTimestamp() {
		return timestamp;
	}
}
