package com.project.SpringClean.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
public class BookingResponse {
    private Long bookingId;
    private Long companyCleanerId;
    private String companyName;
    private Long customerId;
    private String serviceType;
    private Double totalPrice;
    private String address;
    private LocalDate date;        // Much better than String
    private LocalTime time;        // Cleaner time handling
    private Integer hours;         // optional
    private Integer minutes;       // optional
    private String status;
}
