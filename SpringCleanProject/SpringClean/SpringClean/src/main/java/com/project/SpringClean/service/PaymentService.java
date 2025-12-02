package com.project.SpringClean.service;

import com.project.SpringClean.dto.PaymentRequest;
import com.project.SpringClean.dto.PaymentResponse;
import com.project.SpringClean.model.Booking;
import com.project.SpringClean.model.Customer;
import com.project.SpringClean.model.Payment;
import com.project.SpringClean.repository.BookingRepository;
import com.project.SpringClean.repository.CustomerRepository;
import com.project.SpringClean.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepo;

    @Autowired
    private BookingRepository bookingRepo;

    @Autowired
    private CustomerRepository customerRepo;

    public Payment createPayment(PaymentRequest request) {

        Booking booking = bookingRepo.findById(request.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setAmount(request.getAmount());
        payment.setMethod(request.getMethod());
        payment.setStatus("Paid");
        payment.setPaidAt(LocalDateTime.now());

        booking.setStatus("Paid");
        bookingRepo.save(booking);

        return paymentRepo.save(payment);
    }

    public List<PaymentResponse> getPaymentsByCompany(Long companyCleanerId) {
        // Fetch all bookings for the company
        List<Booking> bookings = bookingRepo.findByCompanyCleaner_CompanyCleanerId(companyCleanerId);

        return bookings.stream()
                .map(booking -> {
                    Payment payment = paymentRepo.findByBooking_BookingId(booking.getBookingId())
                            .orElse(null);
                    if (payment == null) return null;

                    Customer customer = booking.getCustomer(); // booking has Customer entity

                    return new PaymentResponse(
                            payment.getPaymentId(),
                            payment.getAmount(),
                            payment.getStatus(),
                            payment.getPaidAt(),
                            booking.getBookingId(),
                            customer.getFirstName(),
                            customer.getLastName()
                    );
                })
                .filter(dto -> dto != null)
                .toList();
    }



}
