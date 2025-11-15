package com.project.SpringClean.controller;

import com.project.SpringClean.dto.CompanyCleanerLoginRequest;
import com.project.SpringClean.dto.CustomerLoginRequest;
import com.project.SpringClean.model.CompanyCleaner;
import com.project.SpringClean.repository.CompanyCleanerRepository;
import com.project.SpringClean.service.CompanyCleanerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/company-cleaners")
@CrossOrigin("http://localhost:3000")
public class CompanyCleanerController {

    @Autowired
    private CompanyCleanerService companyCleanerService;

    @Autowired
    private CompanyCleanerRepository companyCleanerRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @GetMapping
    public List<CompanyCleaner> getAllCompanyCleaners() {
        return companyCleanerService.getAllCompanyCleaners();
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerCustomer(@Valid @RequestBody CompanyCleaner cleaner) {
        if (companyCleanerRepository.existsByEmail(cleaner.getEmail())) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email already registered."));
        }

        companyCleanerRepository.save(cleaner);
        return ResponseEntity.ok(Map.of("message", "Customer registered successfully!"));
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody CompanyCleanerLoginRequest request) {

        if(request.getEmail() == null || request.getPassword() == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Email and password required"));
        }

        Optional<CompanyCleaner> cleaneropt = companyCleanerRepository.findByEmail(request.getEmail());

        if (cleaneropt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Email not found!"));
        }

        CompanyCleaner cleaner = cleaneropt.get();

        // Compare password
        if (!cleaner.getPassword().equals(request.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Incorrect password!"));
        }

        // Success
        return ResponseEntity.ok(
                Map.of(
                        "id", cleaner.getCompanyCleanerId(),
                        "companyName", cleaner.getCompanyName(),
                        "email", cleaner.getEmail(),
                        "message", "Login successful"
                )
        );
    }


}
