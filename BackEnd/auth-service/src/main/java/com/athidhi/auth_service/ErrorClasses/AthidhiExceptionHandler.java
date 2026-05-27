package com.athidhi.auth_service.ErrorClasses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AthidhiExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<AthidhiErrorResponse> handleRegistrationException(AthidhiException ex)
    {
        AthidhiErrorResponse  errorResponse = new AthidhiErrorResponse();
        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setErrorMessage(ex.getMessage());
        errorResponse.setTimestamp(System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler
    public ResponseEntity<AthidhiErrorResponse> handleRegistrationException(Exception ex)
    {
        AthidhiErrorResponse  errorResponse = new AthidhiErrorResponse();
        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setErrorMessage(ex.getMessage());
        errorResponse.setTimestamp(System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
