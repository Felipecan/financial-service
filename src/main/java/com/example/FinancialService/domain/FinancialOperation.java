package com.example.FinancialService.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.FinancialService.dto.FinancialOperationDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

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
@Entity
@Table(name = "financial_operations", indexes = {
		  @Index(name = "ix_account_id", columnList = "accountId"),
		  @Index(name = "id_account_id_status", columnList = "accountId, status")
		})
public class FinancialOperation implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer accountId;
	private String operationType;
	private BigDecimal value;
	private int status;
	private String operationName;
	
	@JsonFormat(pattern="dd/MM/yyyy HH:mm")
	private Date instante;
	
	public FinancialOperation(FinancialOperationDTO operation) {
		
		this.accountId = operation.getAccountId();
		this.value = operation.getValue();
	}
	
	public boolean isValid(Balance balance) {
		
		return balance.getBalanceValue().compareTo(this.value) >= 0;
	}
}
