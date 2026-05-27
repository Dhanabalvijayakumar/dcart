package com.dhanabal.utility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dhanabal.exception.EcommerceException;

@RestControllerAdvice
public class ExceptionControllerAdvice {

	private static final Log LOGGER = LogFactory.getLog(ExceptionControllerAdvice.class);

	@Autowired
	private Environment environment;

	@ExceptionHandler(EcommerceException.class)
	public ResponseEntity<ErrorInfo> eCommerceExceptionHandler(EcommerceException exception) {

		LOGGER.error(exception.getMessage());

		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setErrorCode(HttpStatus.BAD_REQUEST.value());
		errorInfo.setErrorMessage(exception.getMessage());

		return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorInfo> generanExceptionHandler(Exception exception) {

		LOGGER.error(exception.getMessage());

		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		errorInfo.setErrorMessage(exception.getMessage());

		return new ResponseEntity<>(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);

	}

}