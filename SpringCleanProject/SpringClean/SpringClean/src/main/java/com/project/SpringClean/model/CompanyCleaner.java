package com.project.SpringClean.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyCleaner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyCleanerId;

    private String companyName;
    private String contactNumber;
    private String address;


}