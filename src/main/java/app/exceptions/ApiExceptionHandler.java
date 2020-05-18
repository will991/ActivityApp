package app.exceptions;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(value = {ApiRequestException.class})
	public ResponseEntity<Object> handleApiRequestException(ApiRequestException e) {
		final ApiException apiException = new ApiException(
					e.getMessage(),
					e.getHttpStatus(),
					ZonedDateTime.now(ZoneId.of("Z"))
				); 
		return new ResponseEntity<>(apiException, e.getHttpStatus());
	}
}
