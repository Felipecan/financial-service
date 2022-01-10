package com.example.FinancialService.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.FinancialService.domain.FinancialOperation;
import com.example.FinancialService.utils.RabbitMQConstants;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RabbitMQService {

	@Autowired
	private final RabbitTemplate rabbitMQ;
	
	public void insertIntoExecuteOperationQueue(FinancialOperation finOp) {
		
		rabbitMQ.convertAndSend(RabbitMQConstants.EXCHANGE, 
				RabbitMQConstants.QUEUE_TOPIC_KEY_EXECUTE_OPERATION, 
				finOp);
	}	
	
	public void insertIntoNotifyOperationQueue(FinancialOperation finOp) {
		
		rabbitMQ.convertAndSend(RabbitMQConstants.EXCHANGE, 
				RabbitMQConstants.QUEUE_TOPIC_KEY_NOTIFY_OPERATION, 
				finOp);
	}	
}
