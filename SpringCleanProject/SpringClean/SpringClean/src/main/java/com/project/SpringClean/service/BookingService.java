package com.project.SpringClean.service;

import com.project.SpringClean.dto.BookingRequest;
import com.project.SpringClean.model.Booking;
import com.project.SpringClean.model.CompanyCleaner;
import com.project.SpringClean.model.Customer;
import com.project.SpringClean.repository.BookingRepository;
import com.project.SpringClean.repository.CompanyCleanerRepository;
import com.project.SpringClean.repository.CustomerRepository;
import com.project.SpringClean.serviceinterface.BookingServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class BookingService implements BookingServiceInt {

    @Autowired
    private BookingRepository bookingRepo;
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private CompanyCleanerRepository cleanerRepo;

    public BookingService(BookingRepository bookingRepo,
                              CustomerRepository customerRepo,
                              CompanyCleanerRepository cleanerRepo) {
        this.bookingRepo = bookingRepo;
        this.customerRepo = customerRepo;
        this.cleanerRepo = cleanerRepo;
    }

    @Override
    public Booking createBooking(BookingRequest request, Long customerId) {
        // Validate customer
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // Validate cleaner
        CompanyCleaner cleaner = cleanerRepo.findById(request.getCleanerId())
                .orElseThrow(() -> new RuntimeException("Cleaner not found"));

        // Parse date/time
        LocalDate date = LocalDate.parse(request.getDate());
        LocalTime time = LocalTime.parse(request.getTime());

        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setCompanyCleaner(cleaner);
        booking.setAddress(request.getAddress());
        booking.setBookingDate(date);
        booking.setBookingTime(time);
        booking.setHours(request.getHours());
        booking.setMinutes(request.getMinutes());
        booking.setStatus("PENDING");

        return bookingRepo.save(booking);
    }

}
