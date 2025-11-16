package com.project.SpringClean.service;

import com.project.SpringClean.model.CompanyCleaner;
import com.project.SpringClean.repository.CompanyCleanerRepository;
import com.project.SpringClean.serviceinterface.CompanyCleanerServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyCleanerService implements CompanyCleanerServiceInt {

    @Autowired
    private CompanyCleanerRepository companyCleanerRepository;

    public List<CompanyCleaner> getAllCompanyCleaners() {
        return companyCleanerRepository.findAll();
    }

    @Override
    public CompanyCleaner registerCleaner(CompanyCleaner cleaner) {
        if (companyCleanerRepository.existsByEmail(cleaner.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        return companyCleanerRepository.save(cleaner);
    }

    @Override
    public CompanyCleaner login(String email, String password) {
        CompanyCleaner cleaner = companyCleanerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email not found"));

        if(email == null || password == null) {
            throw new RuntimeException("Fields Should not be empty");
        }

        if (!cleaner.getPassword().equals(password)) {
            throw new RuntimeException("Incorrect password");
        }

        return cleaner;
    }

    @Override
    public CompanyCleaner getCompanyCleanerById(Long id) {
        return companyCleanerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cleaner not found"));
    }

}
