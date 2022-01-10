package com.example.FinancialService.services;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.example.FinancialService.config.Properties;
import com.example.FinancialService.domain.Account;
import com.example.FinancialService.services.exceptions.ServiceComunicationException;
import com.example.FinancialService.utils.Utils;

@Service
public class AccountService {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private Properties properties;
	
	private String accountServiceHost;
	
	@PostConstruct
	public void init() {
		
		this.accountServiceHost = properties.getApp().getServices().get(this.getClass().getSimpleName()).getHost();
	}
	
	public Account getAccountFromId(Integer id) {

		try {

			ResponseEntity<Account> response = restTemplate
					.getForEntity(this.accountServiceHost + "/accounts/details/{id}", Account.class, id);

			if (response.getStatusCode().is2xxSuccessful())
				return response.getBody();
			else
				throw new ServiceComunicationException("Account cannot be fetched");
			
		} catch (RestClientResponseException e) {

			String msg = Utils.getErroMessageFromCommunicationService(e.getResponseBodyAsString());
			throw new ServiceComunicationException("Cannot be got account service | " + msg);

		} catch (Exception e) {

			throw new ServiceComunicationException("Cannot be got account service");
		}
	}
}
