package com.example.demowithtests.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.persistence.EntityNotFoundException;

import static com.example.demowithtests.util.exception.ErrorDetails.getResponseEntity;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> resourceNotFoundException(WebRequest request) {
        String requestIdParam = request.getParameter("id");
        String requestDescription = request.getDescription(false);
        String requestId = requestIdParam != null
                ? requestIdParam
                : requestDescription.substring(requestDescription.lastIndexOf('/') +1);
        String message = "Employee not found with id = " + requestId;
        return getResponseEntity(message, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorDetails> handleCustomException(WebRequest request, CustomException ex) {
        String exMessage = ex.getMessage();
        String message = exMessage != null ? exMessage : "CUSTOM EXCEPTION MESSAGE";
        return getResponseEntity(message, request, HttpStatus.BAD_REQUEST);
    }


//    @ExceptionHandler(ResourceWasDeletedException.class)
//    protected ResponseEntity<MyGlobalExceptionHandler> handleDeleteException() {
//        return new ResponseEntity<>(new MyGlobalExceptionHandler("This user was deleted"), HttpStatus.NOT_FOUND);
//    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<?> globleExcpetionHandler(Exception ex, WebRequest request) {
//        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
//        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

//    @Data
//    @AllArgsConstructor
//    private static class MyGlobalExceptionHandler {
//        private String message;
//    }
}
