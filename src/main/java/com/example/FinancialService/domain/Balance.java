package com.example.FinancialService.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.example.FinancialService.domain.enums.FinancialOperationType;

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
public class Balance implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer accountId;
	private BigDecimal balanceValue;
}
