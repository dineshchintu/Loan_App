package com.example.DashBoardService.repository;

import com.example.DashBoardService.entity.KYC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KycRepository  extends JpaRepository<KYC,Long> {
    KYC findByCustomerId(Long customerId);
}
