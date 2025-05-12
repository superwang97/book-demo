package com.example.demo.exception;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {

    private static final ErrorResponse INSTANCE = new ErrorResponse();
    private String message;
    private LocalDateTime timestamp;

    private ErrorResponse() {}

    public static ErrorResponse getInstance() {
        return INSTANCE;
    }
}
