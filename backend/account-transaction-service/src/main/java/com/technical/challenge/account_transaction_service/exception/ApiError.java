package com.technical.challenge.account_transaction_service.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {
    private LocalDate timestamp;
    private int status;
    private String error;
    private String message;

}
