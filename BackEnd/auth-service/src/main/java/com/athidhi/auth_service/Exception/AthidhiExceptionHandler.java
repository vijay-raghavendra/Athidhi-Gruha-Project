package com.athidhi.auth_service.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.stream.Collectors;

@ControllerAdvice
public class AthidhiExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<AthidhiErrorResponse> handleRegistrationException(AthidhiException ex)
    {
        AthidhiErrorResponse  errorResponse = new AthidhiErrorResponse();
        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setStatusType("error");
        errorResponse.setErrorMessage(ex.getMessage());
        errorResponse.setTimestamp(System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler
    public ResponseEntity<AthidhiErrorResponse> handleRegistrationException(Exception ex)
    {
        AthidhiErrorResponse  errorResponse = new AthidhiErrorResponse();
        errorResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.setStatusType("error");
        errorResponse.setErrorMessage(ex.getMessage());
        errorResponse.setTimestamp(System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<AthidhiErrorResponse> handleValidationException(MethodArgumentNotValidException ex)
    {
        String message = ex.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(error -> error.getDefaultMessage())
                        .collect(Collectors.joining(", "));

        AthidhiErrorResponse errorResponse = new AthidhiErrorResponse();

        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setStatusType("error");
        errorResponse.setErrorMessage(message);
        errorResponse.setTimestamp(System.currentTimeMillis());

        return ResponseEntity.badRequest().body(errorResponse);
    }
}
