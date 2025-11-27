package com.project.SpringClean.service;

import com.project.SpringClean.dto.BookingRequest;
import com.project.SpringClean.dto.BookingResponse;
import com.project.SpringClean.dto.BookingUpdateRequest;
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
        CompanyCleaner cleaner = cleanerRepo.findById(request.getCompanyCleanerId())
                .orElseThrow(() -> new RuntimeException("Cleaner not found"));

        // Parse date/time
        LocalDate date = LocalDate.parse(request.getDate());
        LocalTime time = LocalTime.parse(request.getTime());

        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setCompanyCleaner(cleaner);
        booking.setServiceType(request.getServiceType());
        booking.setAddress(request.getAddress());
        booking.setBookingDate(date);
        booking.setBookingTime(time);
        booking.setHours(request.getHours());
        booking.setMinutes(request.getMinutes());
        booking.setStatus("Pending");
        booking.setTotalPrice(request.getTotalPrice());

        return bookingRepo.save(booking);
    }

    public BookingResponse toDTO(Booking booking) {
        BookingResponse dto = new BookingResponse();

        dto.setBookingId(booking.getBookingId());
        dto.setCustomerId(booking.getCustomer().getCustomerId());
        dto.setCompanyCleanerId(booking.getCompanyCleaner().getCompanyCleanerId());
        dto.setCompanyName(booking.getCompanyCleaner().getCompanyName());
        dto.setDate(booking.getBookingDate());
        dto.setTime(booking.getBookingTime());
        dto.setHours(booking.getHours());
        dto.setMinutes(booking.getMinutes());
        dto.setTotalPrice(booking.getTotalPrice());
        dto.setAddress(booking.getAddress());
        dto.setServiceType(booking.getServiceType());
        dto.setStatus(booking.getStatus());

        return dto;

    }

    public List<Booking> getBookingsByCustomer(Long customerId) {
        return bookingRepo.findByCustomer_CustomerId(customerId);
    }

    public Booking updateBooking(Long bookingId, BookingUpdateRequest request){
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        LocalDate date = LocalDate.parse(request.getBookingDate());
        LocalTime time = LocalTime.parse(request.getBookingTime());

        booking.setBookingDate(date);
        booking.setBookingTime(time);

        return bookingRepo.save(booking);
    }

    public void cancelBooking(Long bookingId){
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setStatus("Cancelled");
        bookingRepo.save(booking);
    }

}
