package com.project.SpringClean.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @ManyToOne
    @JoinColumn(name = "customerId", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "companyCleanerId", nullable = false)
    private CompanyCleaner companyCleaner;
    private String serviceType;
    private String address;
    private LocalDate bookingDate;
    private LocalTime bookingTime;
    private Integer hours;
    private Integer minutes;
    private String status;
    private Double totalPrice;
}