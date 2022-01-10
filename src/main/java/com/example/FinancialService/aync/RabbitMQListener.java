package com.example.FinancialService.aync;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.example.FinancialService.domain.FinancialOperation;
import com.example.FinancialService.domain.enums.OperationStatus;
import com.example.FinancialService.services.BalanceService;
import com.example.FinancialService.services.FinancialService;
import com.example.FinancialService.services.RabbitMQService;
import com.example.FinancialService.utils.RabbitMQConstants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RabbitMQListener {
	
	@Autowired
	private BalanceService balanceService;
	
	@Autowired
	private RabbitMQService rabbitService;
	
	@Autowired
	private FinancialService financialService;

	@RabbitListener(queues = RabbitMQConstants.QUEUE_TOPIC_EXECUTE_OPERATION)	
    public void executeOperation(@Payload FinancialOperation financialOperation) {
		
		try {
						
			if (OperationStatus.NOT_EXECUTED.getStatus() == financialOperation.getStatus())
				balanceService.updateBalanceOfAccount(financialOperation);
			
			financialOperation.setStatus(OperationStatus.EXECUTED.getStatus());
			int row = financialService.updateStatusOfFinancialOperation(financialOperation);
			
			if (row < 0) {
				
				financialOperation.setStatus(OperationStatus.PROCESSING.getStatus());
				rabbitService.insertIntoExecuteOperationQueue(financialOperation);
				return;
			}
			
			rabbitService.insertIntoNotifyOperationQueue(financialOperation);
			
		} catch (Exception e) {
		
			log.error(e.getMessage(), e);
			financialOperation.setStatus(OperationStatus.NOT_EXECUTED.getStatus());
			rabbitService.insertIntoExecuteOperationQueue(financialOperation);
		}		
    }
}
