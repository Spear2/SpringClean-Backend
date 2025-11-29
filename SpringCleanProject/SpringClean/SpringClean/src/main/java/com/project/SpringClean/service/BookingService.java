package com.project.SpringClean.service;

import com.project.SpringClean.dto.BookingRequest;
import com.project.SpringClean.dto.BookingResponse;
import com.project.SpringClean.dto.BookingUpdateRequest;
import com.project.SpringClean.model.*;
import com.project.SpringClean.repository.*;
import com.project.SpringClean.serviceinterface.BookingServiceInt;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService implements BookingServiceInt {

    @Autowired
    private BookingRepository bookingRepo;
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private CleanerRepository cleanerRepo;

    @Autowired
    private CompanyCleanerRepository companyCleanerRepo;



    @Override
    @Transactional
    public Booking createBooking(BookingRequest request, Long customerId) {

        // 1. Validate customer
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // 2. Validate company
        CompanyCleaner companyCleaner = companyCleanerRepo.findById(request.getCompanyCleanerId())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        // 3. Determine number of cleaners based on serviceType
        int requiredCleaners;
        switch (request.getServiceType().toLowerCase()) {
            case "basic":
                requiredCleaners = 1;
                break;
            case "standard":
                requiredCleaners = 2;
                break;
            case "premium":
                requiredCleaners = 3;
                break;
            default:
                throw new RuntimeException("Invalid service type");
        }

        // 4. Fetch all cleaners of the company
        List<Cleaner> companyCleanersList = cleanerRepo.findByCompanyCleanerAndAvailableTrue(companyCleaner);

        // 5. Filter available cleaners for requested date and time
        LocalDate bookingDate = LocalDate.parse(request.getDate());
        LocalTime startTime = LocalTime.parse(request.getTime());
        double requestedDuration = request.getHours() + request.getMinutes() / 60.0;
        LocalTime endTime = startTime.plusMinutes((long)(requestedDuration * 60));

        List<Cleaner> availableCleaners = companyCleanersList.stream()
                .filter(cleaner -> {
                    List<Booking> bookingsOnDate = bookingRepo.findByCompanyCleanerAndBookingDate(companyCleaner, bookingDate);
                    // Cleaner is available if they are not assigned to any overlapping booking
                    return bookingsOnDate.stream().noneMatch(b ->
                            b.getAssignedCleaners().contains(cleaner) &&
                                    timesOverlap(startTime, endTime, b.getBookingTime(), b.getBookingTime().plusMinutes((long)((b.getHours() + b.getMinutes()/60.0)*60)))
                    );
                })
                .collect(Collectors.toList());

        if (availableCleaners.size() < requiredCleaners) {
            throw new RuntimeException("Not enough cleaners available for the selected time");
        }

        // 6. Pick required number of cleaners
        List<Cleaner> assignedCleaners = availableCleaners.subList(0, requiredCleaners);

        // 7. Create booking
        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setCompanyCleaner(companyCleaner);
        booking.setAssignedCleaners(assignedCleaners);
        booking.setServiceType(request.getServiceType());
        booking.setAddress(request.getAddress());
        booking.setBookingDate(bookingDate);
        booking.setBookingTime(startTime);
        booking.setHours(request.getHours());
        booking.setMinutes(request.getMinutes());
        booking.setStatus("Pending");
        booking.setTotalPrice(request.getTotalPrice()); // price comes from front-end

        // 8. Save booking
        return bookingRepo.save(booking);
    }


    private int getRequiredCleaners(String serviceType) {
        return switch (serviceType.toLowerCase()) {
            case "basic" -> 1;
            case "standard" -> 2;
            case "premium" -> 3;
            default -> throw new RuntimeException("Invalid service type: " + serviceType);
        };
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

    private boolean timesOverlap(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2) {
        return !start1.isAfter(end2) && !end1.isBefore(start2);
    }

}
