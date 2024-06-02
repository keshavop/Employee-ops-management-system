package com.example.authbackend.models;

import jakarta.persistence.*;
import lombok.Data;

@Data

public class LoginRequestModel {

    private String email;
    private String password;
}
