package com.example.FinancialService.utils;

import java.net.URI;
import java.util.Map;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Utils {

	public static URI buildFinancialUriDefaultFromBasePath(Integer id) {
		
		return ServletUriComponentsBuilder
				.fromPath(Constants.BASE_PATH_SERVICE)
				.path("/{id}")
				.buildAndExpand(id)
				.toUri();
	}
	
	public static String getErroMessageFromCommunicationService(String s) {
		
		String msg = "";
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			
			Map<String, String> map = mapper.readValue(s, Map.class);
			msg = map.get("message");
			
		} catch (JsonProcessingException e1) {
			
			log.error("Error on parsing json message");
		}
		
		return msg;
	}
}
