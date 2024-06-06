package com.example.demowithtests.util.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.mail.AuthenticationFailedException;
import jakarta.mail.SendFailedException;

import static com.example.demowithtests.util.exception.ErrorDetails.getResponseEntityErrorsMap;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(SoftDeleteException.class)
    public ResponseEntity<ErrorDetails> softDeleteException(HttpServletRequest request) {
        return getResponseEntityErrorsMap(request.getRequestURI(),
                                          HttpStatus.CONFLICT,
                                       "Soft delete already done");
    }
    @ExceptionHandler(EmailException.class)
    public ResponseEntity<ErrorDetails> emailArgumentNotValidException(HttpServletRequest request) {
        return getResponseEntityErrorsMap(request.getRequestURI(),
                                          HttpStatus.CONFLICT,
                                        "Email already exist");
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> resourceNotFoundException(HttpServletRequest request) {
        return getResponseEntityErrorsMap(request.getRequestURI(),
                                          HttpStatus.BAD_REQUEST,
                                     "Resource  not found");
    }
    @ExceptionHandler(ResourceWasDeletedException.class)
    protected ResponseEntity<ErrorDetails> handleDeleteException(HttpServletRequest request) {
        return getResponseEntityErrorsMap(request.getRequestURI(),
                                          HttpStatus.NOT_FOUND,
                                  "This user was deleted");
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> globleExcpetionHandler(Exception ex, HttpServletRequest request) {
        return getResponseEntityErrorsMap(request.getRequestURI(),
                                          HttpStatus.BAD_REQUEST,
                                          ex.getMessage());
    }
    @ExceptionHandler(SendFailedException.class)
    public ResponseEntity<ErrorDetails> methodArgumentNotValidException(HttpServletRequest request) {
        return getResponseEntityErrorsMap(request.getRequestURI(),
                                          HttpStatus.BAD_REQUEST,
                                  "Mail sending failed");
    }
    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<ErrorDetails> methodAuthenticationFailedException(HttpServletRequest request) {
        return getResponseEntityErrorsMap(request.getRequestURI(),
                                          HttpStatus.BAD_REQUEST,
                                   "Mail authentication failed");
    }
}
