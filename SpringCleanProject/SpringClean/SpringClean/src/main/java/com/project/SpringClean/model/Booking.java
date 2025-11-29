package com.project.SpringClean.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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

    @ManyToMany
    @JsonBackReference
    @JoinTable(
            name = "booking_cleaners",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "cleaner_id")
    )
    private List<Cleaner> assignedCleaners;

    private String serviceType;
    private String address;
    private LocalDate bookingDate;
    private LocalTime bookingTime;
    private Integer hours;
    private Integer minutes;
    private String status;
    private Double totalPrice;
}