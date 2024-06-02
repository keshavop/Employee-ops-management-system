package com.example.authbackend.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class DeleteEmployeeRequest {
    UUID emplId;
}
