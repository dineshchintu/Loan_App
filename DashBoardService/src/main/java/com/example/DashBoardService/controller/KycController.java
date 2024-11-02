package com.example.DashBoardService.controller;

import com.example.DashBoardService.entity.KYC;
import com.example.DashBoardService.service.KycService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kyc")
public class KycController {

    @Autowired
    KycService kycService;

    // API to apply for a kyc
    @PostMapping("/apply")
    public ResponseEntity<KYC> applyForLoan(@RequestBody KYC kyc){
        KYC createdKyc = kycService.applyForKyc(kyc);
        if(createdKyc==null) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(createdKyc);
    }

    // API to get kyc by customerId
    @GetMapping("/{customerId}")
    public KYC getKycByCustomerId(@PathVariable Long customerId){
        return kycService.getKycByCustomerId(customerId);
    }

    @GetMapping
    public List<KYC> getAllKycs(){
        return kycService.getAllKycs();
    }

    @PutMapping("/update/{kycId}")
    public ResponseEntity<KYC> updateLoan(@PathVariable Long kycId,@RequestBody KYC kyc){
        KYC updatedkyc = kycService.updateKyc(kycId,kyc);
        if(updatedkyc==null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(updatedkyc);
    }



}
