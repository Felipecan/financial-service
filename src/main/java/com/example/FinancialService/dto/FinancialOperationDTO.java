package com.example.FinancialService.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FinancialOperationDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@NotNull(message = "Cannot be null")
	private Integer accountId;
	
	@NotNull(message = "Cannot be null")
	private BigDecimal value;	
}
