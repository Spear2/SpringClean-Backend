package com.project.SpringClean.controller;


import com.project.SpringClean.dto.PaymentRequest;
import com.project.SpringClean.dto.PaymentResponse;
import com.project.SpringClean.model.Booking;
import com.project.SpringClean.model.Payment;
import com.project.SpringClean.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public Payment makePayment(@RequestBody PaymentRequest request) {

        return paymentService.createPayment(request);
    }

    @GetMapping("/company/{companyCleanerId}/payments")
    public ResponseEntity<List<PaymentResponse>> getCompanyPayments(
            @PathVariable Long companyCleanerId) {
        List<PaymentResponse> payments = paymentService.getPaymentsByCompany(companyCleanerId);
        return ResponseEntity.ok(payments);
    }

}