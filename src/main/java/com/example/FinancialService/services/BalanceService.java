package com.example.FinancialService.services;


import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.example.FinancialService.config.Properties;
import com.example.FinancialService.domain.Balance;
import com.example.FinancialService.domain.FinancialOperation;
import com.example.FinancialService.dto.BalanceDTO;
import com.example.FinancialService.services.exceptions.ServiceComunicationException;
import com.example.FinancialService.utils.Utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BalanceService {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private Properties properties;
	
	private String balanceServiceHost;
	
	@PostConstruct
	public void init() {
		
		this.balanceServiceHost = properties.getApp().getServices().get(this.getClass().getSimpleName()).getHost();
	}
	
	public Balance getBalanceFromAccount(Integer accountId) {
		
		try {
			
			ResponseEntity<Balance> response = restTemplate
					.getForEntity(this.balanceServiceHost + "/balances/{accountId}", Balance.class, accountId);
			
			if (response.getStatusCode().is2xxSuccessful())
				return response.getBody();
			else
				throw new ServiceComunicationException("Balance cannot be posted");

		} catch (RestClientResponseException e) {
			
			String msg = Utils.getErroMessageFromCommunicationService(e.getResponseBodyAsString());
			throw new ServiceComunicationException("Cannot be got balance service | " + msg);
			
		} catch (Exception e) {

			throw new ServiceComunicationException("Cannot be got balance service");
		}
	}
	
	public void updateBalanceOfAccount(FinancialOperation finOp) {

		BalanceDTO balance = new BalanceDTO(finOp);

		try {

			restTemplate.put(this.balanceServiceHost + "/balances/{accountId}", balance, balance.getAccountId());
			
		} catch (RestClientResponseException e) {

			String msg = Utils.getErroMessageFromCommunicationService(e.getResponseBodyAsString());
			throw new ServiceComunicationException("Cannot be got balance service | " + msg);

		} catch (Exception e) {

			throw new ServiceComunicationException("Cannot be got balance service");
		}
	}	
}
