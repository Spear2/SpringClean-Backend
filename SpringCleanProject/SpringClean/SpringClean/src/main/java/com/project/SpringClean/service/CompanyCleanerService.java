package com.project.SpringClean.service;

import com.project.SpringClean.model.CompanyCleaner;
import com.project.SpringClean.repository.CompanyCleanerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyCleanerService {

    @Autowired
    private CompanyCleanerRepository companyCleanerRepository;

    public List<CompanyCleaner> getAllCompanyCleaners() {
        return companyCleanerRepository.findAll();
    }

    public CompanyCleaner getCompanyCleanerById(Long id) {
        return companyCleanerRepository.findById(id).orElse(null);
    }

    public CompanyCleaner createCompanyCleaner(CompanyCleaner companyCleaner) {
        return companyCleanerRepository.save(companyCleaner);
    }

    public CompanyCleaner updateCompanyCleaner(Long id, CompanyCleaner companyCleanerDetails) {
        CompanyCleaner companyCleaner = companyCleanerRepository.findById(id).orElse(null);
        if (companyCleaner != null) {
            companyCleaner.setCompanyName(companyCleanerDetails.getCompanyName());
            companyCleaner.setContactNumber(companyCleanerDetails.getContactNumber());
            companyCleaner.setAddress(companyCleanerDetails.getAddress());
            return companyCleanerRepository.save(companyCleaner);
        }
        return null;
    }

    public void deleteCompanyCleaner(Long id) {
        companyCleanerRepository.deleteById(id);
    }
}
