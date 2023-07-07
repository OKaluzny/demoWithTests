package com.example.demowithtests.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.persistence.EntityNotFoundException;

import static com.example.demowithtests.util.exception.ErrorDetails.getResponseEntity;

@ControllerAdvice
public class ServiceExceptionHandler {

    @ExceptionHandler(EmployeeNotFoundException.class)
    protected ResponseEntity<ErrorDetails> handleDeleteException(WebRequest request) {
        return getResponseEntity("This user was deleted", request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ErrorDetails> handleLowerCaseCountries(WebRequest request) {
        return getResponseEntity("No entities found", request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PassportHandedException.class)
    protected ResponseEntity<ErrorDetails> handlePassportHand(WebRequest request) {
        return getResponseEntity("Passport already handed", request, HttpStatus.NOT_FOUND);
    }

}
