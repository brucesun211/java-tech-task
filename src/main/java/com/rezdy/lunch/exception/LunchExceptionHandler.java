package com.rezdy.lunch.exception;

import java.time.format.DateTimeParseException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LunchExceptionHandler {

	/**
	 * Exception handler for DateTimeParseException.
	 * 
	 * If encountering DateTimeParseException, should tell the use that the date
	 * parameter is in wrong format(HttpStatus is 400 , bad request).
	 * 
	 * @param DateTimeParseException.class
	 * @return
	 */
	@ExceptionHandler(DateTimeParseException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(DateTimeParseException exception) {
		return new ResponseEntity<>(new ErrorResponse().setErrorMessage(ErrorEnum.DATE_TIME_PARSER_ERROR.getErrorMsg()),
				HttpStatus.BAD_REQUEST);
	}

	/**
	 * Exception handler for General RuntimeException.
	 * 
	 * If encountering any potential RuntimeExceptions, The Handler will process and
	 * return a friendly message to the user (HttpStatus is 500, internal error).
	 * 
	 * @param RuntimeException.class
	 * @return
	 */
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorResponse> handleGeneralInternalException(RuntimeException exception) {
		return new ResponseEntity<>(new ErrorResponse().setErrorMessage(ErrorEnum.GENERAL_INTERNAL_ERROR.getErrorMsg()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
