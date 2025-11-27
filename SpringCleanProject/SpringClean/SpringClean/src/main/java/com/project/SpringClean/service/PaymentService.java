package com.project.SpringClean.service;

import com.project.SpringClean.dto.PaymentRequest;
import com.project.SpringClean.model.Booking;
import com.project.SpringClean.model.Payment;
import com.project.SpringClean.repository.BookingRepository;
import com.project.SpringClean.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepo;

    @Autowired
    private BookingRepository bookingRepo;

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
}
