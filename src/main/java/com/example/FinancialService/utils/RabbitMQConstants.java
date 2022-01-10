package com.example.FinancialService.utils;

import org.springframework.stereotype.Component;

@Component
public class RabbitMQConstants {

	public static final String EXCHANGE = "direct-exchange";
	
	public static final String QUEUE_TOPIC_EXECUTE_OPERATION = "financial.exchange.topic.queue.execute.operation";
	public static final String QUEUE_TOPIC_KEY_EXECUTE_OPERATION = "financial.exchange.topic.queue.execute.operation.*";
	
	public static final String QUEUE_TOPIC_NOTIFY_OPERATION = "financial.exchange.topic.queue.notify.operation";
	public static final String QUEUE_TOPIC_KEY_NOTIFY_OPERATION  = "financial.exchange.topic.queue.notify.operation.*";
}
