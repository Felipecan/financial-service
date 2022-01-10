package com.example.FinancialService.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentOperationDTO extends FinancialOperationDTO {
	
	private static final long serialVersionUID = 1L;

	private Integer billId;
}
