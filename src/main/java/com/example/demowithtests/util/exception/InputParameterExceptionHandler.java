package com.example.demowithtests.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import static com.example.demowithtests.util.exception.ErrorDetails.getResponseEntity;

@ControllerAdvice
public class InputParameterExceptionHandler {

    @ExceptionHandler(InputParameterException.class)
    protected ResponseEntity<ErrorDetails> handleInputParameterException(WebRequest request) {
        return getResponseEntity("Bad request parameter", request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NumberFormatException.class)
    protected ResponseEntity<ErrorDetails> handleNumberFormatException(WebRequest request) {
        return getResponseEntity("Number format exception", request, HttpStatus.BAD_REQUEST);
    }

//    private ResponseEntity<ErrorDetails> getErrorDetails(String message, WebRequest request) {
//        ErrorDetails errorDetails = new ErrorDetails(new Date(), message, request.getDescription(false));
//        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
//    }
}
