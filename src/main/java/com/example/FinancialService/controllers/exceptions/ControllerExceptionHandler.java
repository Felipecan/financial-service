package com.example.FinancialService.controllers.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.FinancialService.services.exceptions.ObjectNotFoundException;
import com.example.FinancialService.services.exceptions.OperationNotAllowedException;
import com.example.FinancialService.services.exceptions.ServiceComunicationException;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
		
		StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
	
	@ExceptionHandler(ServiceComunicationException.class)
	public ResponseEntity<StandardError> connectService(ServiceComunicationException e, HttpServletRequest request) {
		
		StandardError err = new StandardError(HttpStatus.SERVICE_UNAVAILABLE.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(err);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
		
		ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Validation Error", System.currentTimeMillis());
		for (FieldError x : e.getBindingResult().getFieldErrors()) {
			err.addError(x.getField(), x.getDefaultMessage());
		}		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(PSQLException.class)
	public ResponseEntity<StandardError> connectService(PSQLException e, HttpServletRequest request) {
		
		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(OperationNotAllowedException.class)
	public ResponseEntity<StandardError> operationNotAllowed(OperationNotAllowedException e, HttpServletRequest request) {
		
		StandardError err = new StandardError(HttpStatus.METHOD_NOT_ALLOWED.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(err);
	}
}
