package com.project.SpringClean.controller;

import com.project.SpringClean.model.Customer;
import com.project.SpringClean.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "http://localhost:3000") // adjust your frontend port
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/register")
    public ResponseEntity<?> registerCustomer(@Valid @RequestBody Customer customer) {
        if (customerRepository.existsByEmail(customer.getEmail())) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email already registered."));
        }

        customerRepository.save(customer);
        return ResponseEntity.ok(Map.of("message", "Customer registered successfully!"));
    }
}
