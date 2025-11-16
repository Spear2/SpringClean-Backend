package com.project.SpringClean.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CompanyCleanerLoginResponse {

    private long cleanerId;
    private String token;
    private String message;

    public CompanyCleanerLoginResponse(long cleanerId, String token, String message) {
        this.cleanerId = cleanerId;
        this.token = token;
        this.message = message;
    }


}
