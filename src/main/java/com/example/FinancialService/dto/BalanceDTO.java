package com.example.FinancialService.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.example.FinancialService.domain.FinancialOperation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BalanceDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer accountId;
	private BigDecimal balanceValue;
	private String operationType;
	
	public BalanceDTO(FinancialOperation finOp) {
		
		this.accountId = finOp.getAccountId();
		this.balanceValue = finOp.getValue();
		this.operationType = finOp.getOperationType();
	}
}
