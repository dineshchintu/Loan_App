package com.example.DashBoardService.repository;

import com.example.DashBoardService.entity.LoanTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanTransactionRepository  extends JpaRepository<LoanTransaction,Long> {
    List<LoanTransaction> findAllByCustomerId(Long customerId);
}
