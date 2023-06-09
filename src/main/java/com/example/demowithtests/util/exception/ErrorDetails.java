package com.example.demowithtests.util.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@Getter
@RequiredArgsConstructor
public class ErrorDetails {

    private final Date timestamp;
    private final String message;
    private final String details;

    public static ResponseEntity<ErrorDetails> getResponseEntity(String message, WebRequest request, HttpStatus status) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), message, request.getDescription(true));
        return new ResponseEntity<>(errorDetails, status);
    }
}
