package com.example.FinancialService.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OperationStatus {

	EXECUTED(0),
	NOT_EXECUTED(1),
	PROCESSING(2),
	;
	
	private int status;
}
