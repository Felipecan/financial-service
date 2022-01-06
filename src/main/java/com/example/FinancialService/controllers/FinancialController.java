package com.example.FinancialService.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.FinancialService.services.FinancialService;

@RestController
@RequestMapping(value = "/financial")
public class FinancialController {

	// Transferência de valores entre usuário, 
	// Saque, 
	// Depósito e 
	// Pagamento de contas.
	
	@Autowired
	FinancialService financialService;
	
	public ResponseEntity<?> transfer() {
		
		return null;
	}
	
	public ResponseEntity<?> withdraw() {
		
		return null;
	}
	
	public ResponseEntity<?> deposit() {
		
		return null;
	}	
	
	public ResponseEntity<?> pay() {
		
		return null;
	}	
}
