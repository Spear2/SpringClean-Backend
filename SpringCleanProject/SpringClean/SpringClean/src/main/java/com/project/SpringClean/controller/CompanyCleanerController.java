package com.project.SpringClean.controller;

import com.project.SpringClean.dto.CompanyCleanerLoginRequest;
import com.project.SpringClean.dto.CompanyCleanerLoginResponse;
import com.project.SpringClean.dto.CustomerLoginRequest;
import com.project.SpringClean.model.CompanyCleaner;
import com.project.SpringClean.repository.CompanyCleanerRepository;
import com.project.SpringClean.security.JwtUtil;
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
    JwtUtil jwt;

    @Autowired
    private CompanyCleanerRepository companyCleanerRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @GetMapping
    public List<CompanyCleaner> getAllCompanyCleaners() {
        return companyCleanerService.getAllCompanyCleaners();
    }



    @PostMapping("/register")
    public ResponseEntity<CompanyCleaner> registerCompany(@RequestBody CompanyCleaner companyCleaner) {
        CompanyCleaner saved = companyCleanerService.registerCompanyCleaner(companyCleaner);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody CompanyCleanerLoginRequest request) {
        CompanyCleaner cleaner = companyCleanerService.login(request.getEmail(), request.getPassword());

        String fakeToken = "token-" + cleaner.getCompanyCleanerId();  // For now, manually generate

        return ResponseEntity.ok(
                new CompanyCleanerLoginResponse(
                        cleaner.getCompanyCleanerId(),
                        fakeToken,
                        "Login successful"
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCleaner(@PathVariable Long id) {
        CompanyCleaner cleaner = companyCleanerService.getCompanyCleanerById(id);
        return ResponseEntity.ok(cleaner);
    }



}
