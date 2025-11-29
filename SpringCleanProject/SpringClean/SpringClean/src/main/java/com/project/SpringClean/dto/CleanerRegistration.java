package com.project.SpringClean.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class CleanerRegistration {
    private Long company_id;
    private String cleanerName;
    private String email;
    private Long phoneNumber;
    private String address;
    private String password;
}