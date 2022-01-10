package com.example.FinancialService.controllers;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.FinancialService.domain.FinancialOperation;
import com.example.FinancialService.dto.FinancialOperationDTO;
import com.example.FinancialService.dto.PaymentOperationDTO;
import com.example.FinancialService.dto.TransferOperationDTO;
import com.example.FinancialService.services.FinancialService;
import com.example.FinancialService.utils.Constants;
import com.example.FinancialService.utils.Utils;


@RestController
@RequestMapping(value = Constants.BASE_PATH_SERVICE)
public class FinancialController {
	
	@Autowired
	private FinancialService service;
	
	@PostMapping(value = "/transfer/{fromAccountId}/to/{toAccountId}")
	public ResponseEntity<?> transfer(@Valid @RequestBody TransferOperationDTO operation, 
			@Valid @NotNull @PathVariable Integer fromAccountId, 
			@Valid @NotNull @PathVariable Integer toAccountId) {
		
		operation.setAccountId(fromAccountId);
		operation.setToAccountId(toAccountId);
		
		FinancialOperation finOp = service.transfer(operation);				
				
		return ResponseEntity
				.created(Utils.buildFinancialUriDefaultFromBasePath(finOp.getId()))
				.build();
	}
	
	@PostMapping(value = "/withdraw/from/{accountId}")
	public ResponseEntity<?> withdraw(@Valid @RequestBody FinancialOperationDTO operation, 
			@Valid @NotNull @PathVariable Integer accountId) {
				
		operation.setAccountId(accountId);
		FinancialOperation finOp = service.withdraw(operation);
				
		return ResponseEntity
				.created(Utils.buildFinancialUriDefaultFromBasePath(finOp.getId()))
				.build();
	}
	
	@PostMapping(value = "/deposit/in/{accountId}")
	public ResponseEntity<?> deposit(@Valid @RequestBody FinancialOperationDTO operation, 
			@Valid @NotNull @PathVariable Integer accountId) {
		
		operation.setAccountId(accountId);
		FinancialOperation finOp = service.deposit(operation);
		
		return ResponseEntity
				.created(Utils.buildFinancialUriDefaultFromBasePath(finOp.getId()))
				.build();
	}	
	
	@PostMapping(value = "/payment")
	public ResponseEntity<?> pay(@Valid @RequestBody PaymentOperationDTO operation) {
		
		FinancialOperation finOp = service.pay(operation);
		
		return ResponseEntity
				.created(Utils.buildFinancialUriDefaultFromBasePath(finOp.getId()))
				.build();
	}
	
	@GetMapping(value = "/{id}/account/{accoutId}")
	public ResponseEntity<?> getFinancialOperation(@Valid @NotNull @PathVariable Integer id, 
			@Valid @NotNull @PathVariable Integer accountId) {
		
		FinancialOperation finOp = service.findFinancialOperation(id, accountId);
		return ResponseEntity.ok().body(finOp);
	}
	
	@GetMapping(value = "/from/{accountId}")
	public ResponseEntity<?> getFinancialOperations(@Valid @NotNull @PathVariable Integer accountId) {
		
		List<FinancialOperation> finOps = service.findFinancialOperations(accountId);
		return ResponseEntity.ok().body(finOps);
	}
}
