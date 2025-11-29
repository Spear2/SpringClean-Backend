package com.project.SpringClean.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Cleaner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cleanerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")   // foreign key column
    private CompanyCleaner companyCleaner;

    private String cleanerName;
    private String email;
    private Long phoneNumber;
    private String address;
    private String password;


}