package com.example.FinancialService.domain.enums;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FinancialOperationType {

	DEBIT('-') {
		
		public BigDecimal performOperation(BigDecimal a, BigDecimal b) {
			return a.subtract(b);
		}
	},
	
	CREDIT('+'){
	
		public BigDecimal performOperation(BigDecimal a, BigDecimal b) {
			return a.add(b);
		}
	},
	;
	
	private char symbol;
	
	public abstract BigDecimal performOperation(BigDecimal a, BigDecimal b);
	
	public static FinancialOperationType getOperationType(String opType) {
		
		FinancialOperationType operationType = null;
		
		for (FinancialOperationType ot : FinancialOperationType.values()) {
			
			if (ot.name().equals(opType)) {
				
				operationType = ot;
				break;
			}
		}
		
		return operationType;
	}
	
}
