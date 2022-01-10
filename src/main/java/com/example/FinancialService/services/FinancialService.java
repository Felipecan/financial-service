package com.example.FinancialService.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.FinancialService.domain.Account;
import com.example.FinancialService.domain.Balance;
import com.example.FinancialService.domain.FinancialOperation;
import com.example.FinancialService.domain.enums.FinancialOperationType;
import com.example.FinancialService.domain.enums.OperationStatus;
import com.example.FinancialService.domain.enums.Operations;
import com.example.FinancialService.dto.FinancialOperationDTO;
import com.example.FinancialService.dto.PaymentOperationDTO;
import com.example.FinancialService.dto.TransferOperationDTO;
import com.example.FinancialService.repositories.FinancialRepository;
import com.example.FinancialService.services.exceptions.ObjectNotFoundException;
import com.example.FinancialService.services.exceptions.OperationNotAllowedException;

@Service
public class FinancialService {

	@Autowired
	FinancialRepository repository;
	
	@Autowired
	RabbitMQService rabbitService;
	
	@Autowired
	private AccountService accountService;	

	public FinancialOperation transfer(TransferOperationDTO operation) {
		
		FinancialOperationDTO opFromAccount = new FinancialOperationDTO(operation.getAccountId(), operation.getValue());
		FinancialOperationDTO opToAccount = new FinancialOperationDTO(operation.getToAccountId(), operation.getValue());
		
		FinancialOperation finOp = this.debitOperation(opFromAccount);
		finOp.setOperationName(Operations.TRANSFER.name());
		finOp = this.saveOperation(finOp);
		
		FinancialOperation finOpToAccount = this.creditOperation(opToAccount);
		finOpToAccount.setOperationName(Operations.TRANSFER.name());
		this.saveOperation(finOpToAccount);

		return finOp;
	}

	public FinancialOperation withdraw(FinancialOperationDTO operation) {

		FinancialOperation finOp = this.debitOperation(operation);
		finOp.setOperationName(Operations.WITHDRAW.name());
		
		return this.saveOperation(finOp);
	}

	public FinancialOperation deposit(FinancialOperationDTO operation) {
		
		FinancialOperation finOp = this.creditOperation(operation);
		finOp.setOperationName(Operations.DEPOSIT.name());

		return this.saveOperation(finOp);
	}

	public FinancialOperation pay(PaymentOperationDTO operation) {
		
		FinancialOperation finOp = this.debitOperation(operation);
		finOp.setOperationName(Operations.PAYMENT.name());
		
		return this.saveOperation(finOp);
	}

	public FinancialOperation findFinancialOperation(Integer id, Integer accountId) {
		
		Optional<FinancialOperation> finOp = repository.getFinancialOperation(id, accountId);
		
		return finOp.orElseThrow(() -> new ObjectNotFoundException(
				"Operation with " + id + " for account ID " + accountId + " not found!"));
	}

	public List<FinancialOperation> findFinancialOperations(Integer accountId) {
		
		return repository.getAllFinancialOperationFromAccount(accountId);
	}
	
	public int updateStatusOfFinancialOperation(FinancialOperation finOp) {
		
		return repository.updateStatusOperation(finOp.getStatus(), finOp.getId());
	}
	
	private FinancialOperation debitOperation(FinancialOperationDTO operation) {

		Account account = this.validateAndReturnAccount(operation.getAccountId());

		List<FinancialOperation> operations = repository.getAllOperationNotExecuted(operation.getAccountId());

		Balance realBalance = account.calculateRealBalanceForAccount(operations);

		FinancialOperation finOp = this.createBasicFinancialOperation(operation);
		finOp.setOperationType(FinancialOperationType.DEBIT.name());

		if (!finOp.isValid(realBalance))
			throw new OperationNotAllowedException(
					"Operation not allowed for account ID " + operation.getAccountId() + ". Insufficient balance!");
		
		return finOp;
	}

	private FinancialOperation creditOperation(FinancialOperationDTO operation) {

		this.validateAndReturnAccount(operation.getAccountId());

		FinancialOperation finOp = this.createBasicFinancialOperation(operation);
		finOp.setOperationType(FinancialOperationType.CREDIT.name());

		return finOp;
	}
	
	private Account validateAndReturnAccount(Integer id) {
		
		Account account = accountService.getAccountFromId(id);

		if (!account.isActive())
			throw new OperationNotAllowedException("Operation not allowed for account ID " + id);
		
		return account;
	}
	
	private FinancialOperation createBasicFinancialOperation(FinancialOperationDTO operation) {
		
		FinancialOperation finOp = new FinancialOperation(operation);
		finOp.setInstante(new Date());
		finOp.setStatus(OperationStatus.NOT_EXECUTED.getStatus());
		
		return finOp;
	}
	
	private FinancialOperation saveOperation(FinancialOperation finOp) {
		
		FinancialOperation finOpSaved = repository.save(finOp);		
		rabbitService.insertIntoExecuteOperationQueue(finOpSaved);		
		return finOpSaved;
	}
}
