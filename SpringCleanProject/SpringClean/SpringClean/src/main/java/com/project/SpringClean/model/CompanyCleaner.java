package com.project.SpringClean.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyCleaner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyCleanerId;
    private String companyName;
    private String email;
    private String phoneNumber;
    private String address;
    private String password;

    @OneToMany(mappedBy = "companyCleaner", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Cleaner> cleaners;
}