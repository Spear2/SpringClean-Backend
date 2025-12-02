package com.project.SpringClean.repository;


import com.project.SpringClean.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByBooking_BookingId(Long bookingId);
}
