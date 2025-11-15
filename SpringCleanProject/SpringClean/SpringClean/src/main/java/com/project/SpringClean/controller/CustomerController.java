package com.project.SpringClean.controller;

import com.project.SpringClean.dto.CustomerLoginRequest;
import com.project.SpringClean.model.Customer;
import com.project.SpringClean.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "http://localhost:3000")
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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody CustomerLoginRequest request) {

        if(request.getEmail() == null || request.getPassword() == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Email and password required"));
        }

        Optional<Customer> customerOpt = customerRepository.findByEmail(request.getEmail());

        if (customerOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Email not found!"));
        }

        Customer customer = customerOpt.get();

        // Compare password
        if (!customer.getPassword().equals(request.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Incorrect password!"));
        }

        // Success
        return ResponseEntity.ok(
                Map.of(
                        "id", customer.getCustomerId(),
                        "fname", customer.getFirstName(),
                        "lname", customer.getLastName(),
                        "email", customer.getEmail(),
                        "message", "Login successful"
                )
        );
    }
}
