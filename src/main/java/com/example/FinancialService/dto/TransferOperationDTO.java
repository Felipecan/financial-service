package com.example.FinancialService.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TransferOperationDTO extends FinancialOperationDTO {

	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "Cannot be null")
	private Integer toAccountId;
}
