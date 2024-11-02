package com.example.DashBoardService.controller;

import com.example.DashBoardService.entity.LoanTransaction;
import com.example.DashBoardService.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    LoanService dashboardService;

    // API to apply for a new loan directly with LoanTransaction as the request body
    @PostMapping("/apply")
    public ResponseEntity<LoanTransaction> applyForLoan(@RequestBody LoanTransaction loanTransaction){
        LoanTransaction createdLoan = dashboardService.applyForLoan(loanTransaction);
        if(createdLoan==null) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLoan);
    }

    // API to get all loans by customerId
    @GetMapping("/customer/{customerId}")
    public List<LoanTransaction> getAllLoansByCustomerId(@PathVariable Long customerId){
        return dashboardService.getAllLoansByCustomerId(customerId);
    }
    @GetMapping("/loan/{loanId}")
        public LoanTransaction getLoanById(@PathVariable Long loanId){
            return dashboardService.getLoanById(loanId);
        }


    @GetMapping
    public List<LoanTransaction> getAllLoans(){
        return dashboardService.getAllLoans();
    }

    @PutMapping("/update/{loanId}")
    public ResponseEntity<LoanTransaction> updateLoan(@PathVariable Long loanId,@RequestBody LoanTransaction loanTransaction){
        LoanTransaction updatedLoan = dashboardService.updateLoan(loanId,loanTransaction);
        if(updatedLoan==null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(updatedLoan);
    }

}
