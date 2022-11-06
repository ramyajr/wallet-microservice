package com.leovegas.wallet.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.leovegas.wallet.exception.*;

import java.sql.Timestamp;

/**
 * Class to handle all the global and specific exceptions in player wallet service
 *
 */
@ControllerAdvice
public class WalletlExceptionHandler {

	/**
	 * Method to handle the specific Exception EntityNotFoundException
	 * 
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ExceptionDetails> handleEntityNotFoundException(EntityNotFoundException exception) {
		ExceptionDetails exceptionDetails = ExceptionDetails.builder()
				.timeStamp(new Timestamp(System.currentTimeMillis())).status(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.message(exception.getMessage()).details(exception.getClass().getName()).build();
		return new ResponseEntity<>(exceptionDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Method to handle the specific Exception EntityNotFoundException
	 *
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(InvalidTransactionException.class)
	public ResponseEntity<ExceptionDetails> handleInvalidTransactionException(InvalidTransactionException exception) {
		ExceptionDetails exceptionDetails = ExceptionDetails.builder()
				.timeStamp(new Timestamp(System.currentTimeMillis())).status(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.message(exception.getMessage()).details(exception.getClass().getName()).build();
		return new ResponseEntity<>(exceptionDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Method to handle the Global Exception
	 * 
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionDetails> handleGlobalException(Exception exception) {
		ExceptionDetails exceptionDetails = ExceptionDetails.builder()
				.timeStamp(new Timestamp(System.currentTimeMillis())).status(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.message(exception.getMessage()).details(exception.getClass().getName()).build();
		return new ResponseEntity<>(exceptionDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
