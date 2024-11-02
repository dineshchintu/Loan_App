package com.example.DashBoardService.controller;

import com.example.DashBoardService.service.ApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/approval")
public class ApprovalController {
    @Autowired
    ApprovalService approvalService;

    @PostMapping("/loan/approve/{loanId}")
    public ResponseEntity<String> approveLoan(@PathVariable Long loanId, @RequestBody String reason){
        String result= approvalService.approveLoan(loanId,reason);
        switch (result){
            case "Loan not found", "KYC not found":
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
            case "KYC not completed":
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
            case "Loan Approved":
                return ResponseEntity.ok(result);
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected Error");
        }
    }
    @PostMapping("/loan/reject/{loanId}")
    public ResponseEntity<String> rejectLoan(@PathVariable Long loanId,@RequestBody String reason){
        String result = approvalService.rejectLoan(loanId,reason);
        if(result==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Loan not found");
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("kyc/approve/{kycId}")
    public ResponseEntity<String> approveKyc(@PathVariable Long kycId,@RequestBody String reason){
        String result = approvalService.approveKyc(kycId,reason);
        if(result==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kyc not found");
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("kyc/reject/{kycId}")
    public ResponseEntity<String> rejectKyc(@PathVariable Long kycId,@RequestBody String reason){
        String result = approvalService.rejectKyc(kycId,reason);
        if(result==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kyc not found");
        }
        return ResponseEntity.ok(result);
    }

}
