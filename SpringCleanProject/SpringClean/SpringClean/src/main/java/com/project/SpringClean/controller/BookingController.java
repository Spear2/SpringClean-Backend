package com.project.SpringClean.controller;

import com.project.SpringClean.dto.BookingRequest;
import com.project.SpringClean.dto.BookingResponse;
import com.project.SpringClean.dto.BookingUpdateRequest;
import com.project.SpringClean.model.Booking;
import com.project.SpringClean.model.Customer;
import com.project.SpringClean.repository.BookingRepository;
import com.project.SpringClean.service.BookingService;
import com.project.SpringClean.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingRepository bookingRepository;

    @PostMapping("/{customerId}")
    public ResponseEntity<?> createBooking(
            @RequestBody BookingRequest request,
            @PathVariable Long customerId
    ) {
        try {
            Booking saved = bookingService.createBooking(request, customerId);
            return ResponseEntity.ok(saved);
        } catch (Exception ex) {
            ex.printStackTrace(); // <----- ADD THIS
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/customers/{customerId}/bookings")
    public List<BookingResponse> getBookings(@PathVariable Long customerId) {
        return bookingService.getBookingsByCustomer(customerId)
                .stream()
                .map(bookingService::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/company/{companyCleanerId}/bookings")
    public List<BookingResponse> getCompanyBookings(@PathVariable Long companyCleanerId) {
        return bookingService.getBookingsByCompany(companyCleanerId)
                .stream()
                .map(bookingService::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<Booking> findByCustomer(@PathVariable Long customerId) {
        return bookingRepository.findByCustomer_CustomerId(customerId);
    }

    @PutMapping("/{bookingId}")
    public ResponseEntity<?> updateBooking(
            @PathVariable Long bookingId,
            @RequestBody BookingUpdateRequest req) {

        try {
            Booking updated = bookingService.updateBooking(bookingId, req);
            return ResponseEntity.ok(updated);

        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<?> cancelBooking(@PathVariable Long bookingId) {

        try {
            bookingService.cancelBooking(bookingId);
            return ResponseEntity.ok("Booking cancelled");

        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }



}

