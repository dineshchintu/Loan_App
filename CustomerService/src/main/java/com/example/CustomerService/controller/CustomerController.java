package com.example.CustomerService.controller;

import com.example.CustomerService.dto.CustomerLoginDto;
import com.example.CustomerService.entity.Customer;
import com.example.CustomerService.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers(){
        List<Customer> users = customerService.getAllCustomers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/register")
    public ResponseEntity<Customer> register(@RequestBody Customer customer){
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.registerUser(customer));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody CustomerLoginDto loginDto){
        Map<String, Object> response = customerService.login(loginDto);
        if(response!=null){
            return ResponseEntity.ok(response);
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id){
        Customer customer = customerService.getCustomerById(id);
        if(customer==null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(customer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id,@RequestBody Customer customer){
        return ResponseEntity.ok(customerService.updateCustomer(id,customer));
    }

    @PostMapping("/{customerId}/addLoanId")
    public ResponseEntity<Void> addLoadIdToCustomer(@PathVariable Long customerId,@RequestBody Long loanId){
        customerService.addLoanId(customerId,loanId);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/{customerId}/addKycId")
    public ResponseEntity<Void> addKycToCustomer(@PathVariable Long customerId,@RequestBody Long kycId){
        customerService.addKycId(customerId,kycId);
        return ResponseEntity.ok().build();
    }

}
