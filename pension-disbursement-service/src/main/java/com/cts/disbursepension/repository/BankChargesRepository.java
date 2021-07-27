package com.cts.disbursepension.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.disbursepension.model.BankCharges;

/**
 * Repository for Bank Charges
 * @author Anas Zubair
 *
 */
@Repository
public interface BankChargesRepository extends JpaRepository<BankCharges, Integer> {
	
	List<BankCharges> findByBankType(String bankType);

}
