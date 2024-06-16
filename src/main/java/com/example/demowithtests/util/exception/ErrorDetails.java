package com.example.demowithtests.util.exception;

import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Date;


@Builder
public record ErrorDetails (
     Date timestamp,
     String details,
     String path){
    public static ResponseEntity<ErrorDetails> getResponseEntityErrorsMap(
                                                                           String path,
                                                                           HttpStatus status,
                                                                           String errors) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .timestamp(new Date())
                .path(path)
                .details(errors)
                .build();
        return new ResponseEntity<>(errorDetails, status);
    }
}
