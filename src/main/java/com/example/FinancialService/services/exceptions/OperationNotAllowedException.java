package com.example.FinancialService.services.exceptions;

public class OperationNotAllowedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public OperationNotAllowedException(String s) {
		super(s);
	}
	
	public OperationNotAllowedException(String s, Throwable th) {
		super(s, th);
	}
}

