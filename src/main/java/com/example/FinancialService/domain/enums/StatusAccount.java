package com.example.FinancialService.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusAccount {

	ACTIVE(0),
	INACTIVE(1),
	;
	
	private int status;
}