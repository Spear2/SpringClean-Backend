package com.project.SpringClean.controller;

import com.project.SpringClean.model.CompanyCleaner;
import com.project.SpringClean.service.CompanyCleanerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company-cleaners")
@CrossOrigin(origins = "http://localhost:3000") // Adjust if your React runs elsewhere
public class CompanyCleanerController {

    @Autowired
    private CompanyCleanerService companyCleanerService;

    @GetMapping
    public List<CompanyCleaner> getAllCompanyCleaners() {
        return companyCleanerService.getAllCompanyCleaners();
    }

    @GetMapping("/{id}")
    public CompanyCleaner getCompanyCleanerById(@PathVariable Long id) {
        return companyCleanerService.getCompanyCleanerById(id);
    }

    @PostMapping
    public CompanyCleaner createCompanyCleaner(@RequestBody CompanyCleaner companyCleaner) {
        return companyCleanerService.createCompanyCleaner(companyCleaner);
    }

    @PutMapping("/{id}")
    public CompanyCleaner updateCompanyCleaner(@PathVariable Long id, @RequestBody CompanyCleaner companyCleanerDetails) {
        return companyCleanerService.updateCompanyCleaner(id, companyCleanerDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteCompanyCleaner(@PathVariable Long id) {
        companyCleanerService.deleteCompanyCleaner(id);
    }
}
