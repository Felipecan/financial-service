package com.example.FinancialService.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.example.FinancialService.domain.enums.FinancialOperationType;
import com.example.FinancialService.domain.enums.StatusAccount;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Account implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;
	private String identificationDocument;
	private Integer status;
	private BigDecimal balance;
	
	public Balance calculateRealBalanceForAccount(List<FinancialOperation> operations) {
		
		BigDecimal value = this.balance;
		
		for (FinancialOperation op : operations) {
			
			FinancialOperationType opType = FinancialOperationType.getOperationType(op.getOperationType());
			value = opType.performOperation(value, op.getValue());
		}
		
		return new Balance(this.id, value);
	}
	
	public boolean isActive() {
		
		return StatusAccount.ACTIVE.getStatus() == status;
	}
}
