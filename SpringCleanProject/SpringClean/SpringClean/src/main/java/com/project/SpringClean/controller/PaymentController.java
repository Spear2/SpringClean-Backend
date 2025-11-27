package com.project.SpringClean.controller;

import com.project.SpringClean.dto.PaymentRequest;
import com.project.SpringClean.model.Payment;
import com.project.SpringClean.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public Payment makePayment(@RequestBody PaymentRequest request) {
        return paymentService.createPayment(request);
    }
}