package com.spring.qburst.demoApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestReponseEntityExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(value = StudentException.class)
	 public ResponseEntity<Object> exception(StudentException exception) {
	      return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
	 }
}
