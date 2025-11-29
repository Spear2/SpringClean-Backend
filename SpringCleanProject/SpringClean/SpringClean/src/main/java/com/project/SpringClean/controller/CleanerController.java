package com.project.SpringClean.controller;


import com.project.SpringClean.dto.CleanerRegistration;
import com.project.SpringClean.model.Cleaner;

import com.project.SpringClean.repository.CleanerRepository;
import com.project.SpringClean.service.CleanerService;
import com.project.SpringClean.service.CompanyCleanerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cleaners")
@CrossOrigin("http://localhost:3000")
public class CleanerController  {
    @Autowired
    private CleanerService cleanerService;

    @Autowired
    private CleanerRepository cleanerRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody CleanerRegistration dto) {
        Cleaner saved = cleanerService.registerCleaner(dto);
        return ResponseEntity.ok(saved);
    }

}