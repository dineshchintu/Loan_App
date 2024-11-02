package com.example.CustomerService.controller;

import com.example.CustomerService.entity.Admin;
import com.example.CustomerService.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<String> adminLogin(@RequestBody Admin admin){
        String username = admin.getUsername();
        String password = admin.getPassword();
        String token = "Admin-Token";
        if(adminService.validateAdminCredentials(username,password)){
            return ResponseEntity.ok("Login successful! Token: " + token);
        }
        else{
            return ResponseEntity.status(401).body("Invalid credentials! Access denied.");
        }
    }

}
