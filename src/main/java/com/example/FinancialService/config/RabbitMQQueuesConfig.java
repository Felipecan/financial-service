package com.example.FinancialService.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.FinancialService.utils.RabbitMQConstants;

import lombok.Getter;

@Getter
@EnableRabbit
@Configuration
public class RabbitMQQueuesConfig {

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(RabbitMQConstants.EXCHANGE);
    }
    
    @Bean
    public Queue queueExecuteOperation() {
    	
        return new Queue(RabbitMQConstants.QUEUE_TOPIC_EXECUTE_OPERATION, true, false, false);
    }

    @Bean
    public Binding bindingExecuteOperation() {
    	
        return BindingBuilder
        		.bind(queueExecuteOperation())
        		.to(exchange())
        		.with(RabbitMQConstants.QUEUE_TOPIC_KEY_EXECUTE_OPERATION);
    }
    
    @Bean
    public Queue queueNotifyOperation() {
    	
        return new Queue(RabbitMQConstants.QUEUE_TOPIC_NOTIFY_OPERATION, true, false, false);
    }

    @Bean
    public Binding bindingNotifyOperation() {
    	
        return BindingBuilder
        		.bind(queueNotifyOperation())
        		.to(exchange())
        		.with(RabbitMQConstants.QUEUE_TOPIC_KEY_NOTIFY_OPERATION);
    }
}
