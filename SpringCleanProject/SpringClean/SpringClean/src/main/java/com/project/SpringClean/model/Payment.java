package com.project.SpringClean.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @OneToOne
    @JoinColumn(name = "bookingId", nullable = false)
    private Booking booking;

    private double paymentAmount;
    private LocalDate paymentDate;
}