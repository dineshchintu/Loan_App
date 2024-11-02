package com.example.DashBoardService.service;

import com.example.DashBoardService.entity.KYC;
import com.example.DashBoardService.entity.LoanTransaction;
import com.example.DashBoardService.repository.KycRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class KycService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    KycRepository kycRepository;


    @Transactional
    public KYC applyForKyc(KYC kyc) {
        KYC newKyc = new KYC();
        newKyc.setAadhaarNumber(kyc.getAadhaarNumber());
        newKyc.setCustomerId(kyc.getCustomerId());
        newKyc.setApplicationStatus("Pending");
        newKyc.setPanNumber(kyc.getPanNumber());
        newKyc.setDocumentsSubmitted(false);

        // Save newKyc to generate the kycId
        KYC createdKyc = kycRepository.save(newKyc);

        String customerServiceUrl = "http://localhost:8001/customer/" + createdKyc.getCustomerId() + "/addKycId";
        try {
            // Attempt to send the KYC ID to the customer service
            System.out.println(createdKyc.getKycId());
            restTemplate.postForObject(customerServiceUrl, createdKyc.getKycId(), Void.class);

            // If successful, return the created KYC
            return createdKyc;

        } catch (Exception e) {
            // Log the error
            System.err.println("Error while contacting customer service: " + e.getMessage());

            // Rethrow the exception to trigger a transaction rollback
            throw new RuntimeException("Failed to notify customer service", e);
        }
    }


    public KYC getKycByCustomerId(Long customerId) {
        return kycRepository.findByCustomerId(customerId);
    }

    public List<KYC> getAllKycs() {
        return kycRepository.findAll();
    }

    public KYC updateKyc(Long kycId, KYC kyc) {
        KYC existingKYC =  kycRepository.findById(kycId).orElse(null);
        System.out.println(existingKYC);
        if(existingKYC==null) return  null;
        existingKYC.setDocumentsSubmitted(kyc.isDocumentsSubmitted());
        existingKYC.setPanNumber(kyc.getPanNumber());
        existingKYC.setAadhaarNumber(kyc.getAadhaarNumber());
        existingKYC.setApplicationStatus(kyc.getApplicationStatus());
        KYC updatedKyc = kycRepository.save(existingKYC);
        return updatedKyc;
    }
}
