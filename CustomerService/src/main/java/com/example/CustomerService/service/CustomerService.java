package com.example.CustomerService.service;

import com.example.CustomerService.dto.CustomerLoginDto;
import com.example.CustomerService.entity.Customer;
import com.example.CustomerService.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer registerUser(Customer customer) {
        return customerRepository.save(customer);
    }

    public Map<String, Object> login(CustomerLoginDto loginDto) {
        Customer customer = customerRepository.findByEmail(loginDto.getEmail());
        if(customer!=null && customer.getPassword().equals(loginDto.getPassword())){
            String token = "Customer-Token";
            return Map.of("token",token,"user",customer);
        }
        return null;

    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    public Customer updateCustomer(Long id, Customer customer) {
        Customer existingCustomer = customerRepository.findById(id).orElseThrow(()-> new RuntimeException("User not found"));
        existingCustomer.setEmail(customer.getEmail());
        existingCustomer.setFirstName(customer.getFirstName());
        existingCustomer.setLastName(customer.getLastName());
        existingCustomer.setPassword(customer.getPassword());
        existingCustomer.setLoanids(customer.getLoanids());
        return customerRepository.save(existingCustomer);
    }

    public void addLoanId(Long customerId, Long loanId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(()-> new RuntimeException("Customer not found with ID: " + customerId));
        customer.getLoanids().add(loanId);
        customerRepository.save(customer);
    }

    public void addKycId(Long customerId, Long kycId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(()-> new RuntimeException("Customer not found with ID: " + customerId));
        customer.setKycId(kycId);
        customerRepository.save(customer);
    }
}
