package com.example.DashBoardService.service;

import com.example.DashBoardService.entity.KYC;
import com.example.DashBoardService.entity.LoanTransaction;
import com.example.DashBoardService.repository.KycRepository;
import com.example.DashBoardService.repository.LoanTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApprovalService {

    @Autowired
    LoanTransactionRepository loanTransactionRepository;

    @Autowired
    KycRepository kycRepository;

    public String approveLoan(Long loanId, String reason) {
        LoanTransaction loan = loanTransactionRepository.findById(loanId).orElse(null);
        if(loan==null) return "Loan not found";
        KYC kyc =kycRepository.findByCustomerId(loan.getCustomerId());
        if(kyc==null) return "KYC not found";
        if(!"Completed".equals(kyc.getApplicationStatus())){
            return "KYC not completed";
        }
        loan.setLoanStatus("Approved");
        loan.setAdminReason(reason);
        loanTransactionRepository.save(loan);
        return "Loan Approved";
    }

    public String rejectLoan(Long loanId, String reason) {
        LoanTransaction loan = loanTransactionRepository.findById(loanId).orElse(null);
        if(loan==null) return null;
        loan.setLoanStatus("Rejected");
        loan.setAdminReason(reason);
        loanTransactionRepository.save(loan);
        return "Loan Rejected";
    }

    public String approveKyc(Long kycId, String reason) {
        KYC kyc = kycRepository.findById(kycId).orElse(null);
        if(kyc==null) return null;
        kyc.setApplicationStatus("Completed");
        kycRepository.save(kyc);
        return "KYC Completed";

    }

    public String rejectKyc(Long kycId, String reason) {
        KYC kyc = kycRepository.findById(kycId).orElse(null);
        if(kyc==null) return null;
        kyc.setApplicationStatus("Rejected");
        kyc.setRejectionReason(reason);
        kycRepository.save(kyc);
        return "KYC Rejected";
    }
}
