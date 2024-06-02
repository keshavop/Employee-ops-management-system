package com.example.authbackend.models;

import lombok.Data;

@Data
public class LoginResponseModel {

    private String jwtToken;
    private String username;


    public LoginResponseModel() {}

    public LoginResponseModel(String jwtToken, String username) {
        this.jwtToken = jwtToken;
        this.username = username;
    }


}
