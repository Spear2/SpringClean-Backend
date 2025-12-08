package com.project.SpringClean.service;

import com.project.SpringClean.dto.CleanerRegistration;
import com.project.SpringClean.model.Booking;
import com.project.SpringClean.model.Cleaner;
import com.project.SpringClean.model.CompanyCleaner;
import com.project.SpringClean.model.Customer;
import com.project.SpringClean.repository.BookingRepository;
import com.project.SpringClean.repository.CleanerRepository;
import com.project.SpringClean.repository.CompanyCleanerRepository;
import com.project.SpringClean.serviceinterface.CleanerServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class CleanerService implements CleanerServiceInt {

    @Autowired
    private CleanerRepository cleanerRepository;

    @Autowired
    private CompanyCleanerRepository companyCleanerRepository;
    private BookingRepository bookingRepository;

    @Override
    public Cleaner getCleanerById(Long id) {
        return cleanerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cleaner not found"));
    }


    public Cleaner registerCleaner(CleanerRegistration dto) {

        CompanyCleaner company = companyCleanerRepository
                .findById(dto.getCompanyCleanerId())
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

    public Cleaner login(String email, String password){
        Cleaner cleaner = cleanerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email not found"));
        if(email == null || password == null) {
            throw new RuntimeException("Fields Should not be empty");
        }

        if (!cleaner.getPassword().equals(password)) {
            throw new RuntimeException("Incorrect password");
        }


        return cleaner;
    }


}