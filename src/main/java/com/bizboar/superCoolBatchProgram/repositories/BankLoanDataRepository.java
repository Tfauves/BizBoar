package com.bizboar.superCoolBatchProgram.repositories;

import com.bizboar.superCoolBatchProgram.domain.BankLoanData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankLoanDataRepository extends JpaRepository<BankLoanData, Long> {
}
