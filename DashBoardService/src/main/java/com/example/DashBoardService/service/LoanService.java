package com.example.DashBoardService.service;

import com.example.DashBoardService.entity.LoanTransaction;
import com.example.DashBoardService.repository.LoanTransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class LoanService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    LoanTransactionRepository loanTransactionRepository;

    @Transactional
    public LoanTransaction applyForLoan(LoanTransaction loanTransaction) {
        LoanTransaction newLoan = new LoanTransaction();
        newLoan.setLoanAmount(loanTransaction.getLoanAmount());
        newLoan.setLoanStatus("Pending");
        newLoan.setCustomerId(loanTransaction.getCustomerId());
        newLoan.setLoandescription(loanTransaction.getLoandescription());
        newLoan.setCustomerReason(loanTransaction.getCustomerReason());

        // Save newLoan to generate the loanId
        LoanTransaction createdLoan = loanTransactionRepository.save(newLoan);

        String customerServiceUrl = "http://localhost:8001/customer/" + createdLoan.getCustomerId() + "/addLoanId";
        try {
            // Attempt to send the loan ID to the customer service
            System.out.println(createdLoan.getLoanId());
            restTemplate.postForObject(customerServiceUrl, createdLoan.getLoanId(), Void.class);

            // If successful, return the created loan
            return createdLoan;

        } catch (Exception e) {
            // Log the error
            System.err.println("Error while contacting customer service: " + e.getMessage());

            // Rethrow the exception to trigger a transaction rollback
            throw new RuntimeException("Failed to notify customer service", e);
        }
    }


    public List<LoanTransaction> getAllLoansByCustomerId(Long customerId) {
        return loanTransactionRepository.findAllByCustomerId(customerId);
    }

    public List<LoanTransaction> getAllLoans() {
        return loanTransactionRepository.findAll();
    }

    public LoanTransaction updateLoan(Long loanId, LoanTransaction loanTransaction) {
        LoanTransaction existingLoan =  loanTransactionRepository.findById(loanId).orElse(null);
        if(existingLoan==null) return null;
        existingLoan.setCustomerReason(loanTransaction.getCustomerReason());
        existingLoan.setLoandescription(loanTransaction.getLoandescription());
        existingLoan.setLoanAmount(loanTransaction.getLoanAmount());
        existingLoan.setLoanStatus(loanTransaction.getLoanStatus());
        LoanTransaction updatedLoan = loanTransactionRepository.save(existingLoan);
        return updatedLoan;
    }

    public LoanTransaction getLoanById(Long loanId) {
        return loanTransactionRepository.findById(loanId).orElse(null);
    }
}
