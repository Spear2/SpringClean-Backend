package com.project.SpringClean.service;

import com.project.SpringClean.dto.CleanerRegistration;
import com.project.SpringClean.model.Cleaner;
import com.project.SpringClean.model.CompanyCleaner;
import com.project.SpringClean.repository.CleanerRepository;
import com.project.SpringClean.repository.CompanyCleanerRepository;
import com.project.SpringClean.serviceinterface.CleanerServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class CleanerService implements CleanerServiceInt {

    @Autowired
    private CleanerRepository cleanerRepository;

    @Autowired
    private CompanyCleanerRepository companyCleanerRepository;


    public Cleaner registerCleaner(CleanerRegistration dto) {

        CompanyCleaner company = companyCleanerRepository
                .findById(dto.getCompany_id())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        Cleaner cleaner = new Cleaner();
        cleaner.setCleanerName(dto.getCleanerName());
        cleaner.setEmail(dto.getEmail());
        cleaner.setPhoneNumber(dto.getPhoneNumber()); // should be String
        cleaner.setAddress(dto.getAddress());
        cleaner.setPassword(dto.getPassword());
        cleaner.setCompanyCleaner(company);

        return cleanerRepository.save(cleaner);
    }


}