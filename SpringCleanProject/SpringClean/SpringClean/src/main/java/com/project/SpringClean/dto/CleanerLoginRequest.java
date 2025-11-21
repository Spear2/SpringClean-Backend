package com.project.SpringClean.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CleanerLoginRequest {
    private  String username;
    private String password;

    public CleanerLoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
