package com.example.FinancialService.repositories;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.FinancialService.domain.FinancialOperation;

@Repository
public interface FinancialRepository extends JpaRepository<FinancialOperation, Integer> {

	@Query(value = ""
			     + " SELECT "
			     + "   * "
			     + " FROM "
			     + "   financial_operations "
			     + " WHERE "
			     + "   account_id = ? "
			     + "   AND status = 1 ", 
			nativeQuery = true)
	public List<FinancialOperation> getAllOperationNotExecuted(Integer accountId);
	
	@Query(value = ""
			     + " SELECT "
			     + "   * "
			     + " FROM "
			     + "   financial_operations "
			     + " WHERE "
			     + "   id = ? "
			     + "   AND account_id = ? ", 
			nativeQuery = true)
	public Optional<FinancialOperation> getFinancialOperation(Integer id, Integer accountId);
	
	@Query(value = ""
			     + " SELECT "
			     + "   * "
			     + " FROM "
			     + "   financial_operations "
			     + " WHERE "
			     + "   account_id = ? "
			     + "   ORDER BY 1 ASC ", 
			nativeQuery = true)
	public List<FinancialOperation> getAllFinancialOperationFromAccount(Integer accountId);
	
	@Transactional(rollbackOn = Exception.class)
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query(value = ""
			     + " UPDATE "
			     + "   financial_operations "
			     + " SET "
			     + "   status = ? "
			     + " WHERE "
			     + "   id = ? ", 
			nativeQuery = true)
	public int updateStatusOperation(Integer status, Integer id);
}
