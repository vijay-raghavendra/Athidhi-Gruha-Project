package com.athidhi.auth_service.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.stream.Collectors;
import com.athidhi.auth_service.Logging.LoggingConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ControllerAdvice
public class AthidhiExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingConstants.EXCEPTION_LOGGER);

    @ExceptionHandler
    public ResponseEntity<AthidhiErrorResponse> handleAthidhiException(AthidhiException ex)
    {
        AthidhiErrorResponse  errorResponse = new AthidhiErrorResponse();
        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setStatusType("error");
        errorResponse.setErrorMessage(ex.getMessage());
        errorResponse.setTimestamp(System.currentTimeMillis());

        showStackTrace(ex);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler
    public ResponseEntity<AthidhiErrorResponse> handleGenericException(Exception ex)
    {
        AthidhiErrorResponse  errorResponse = new AthidhiErrorResponse();
        errorResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.setStatusType("error");
        errorResponse.setErrorMessage(ex.getMessage());
        errorResponse.setTimestamp(System.currentTimeMillis());

        showStackTrace(ex);

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

        showStackTrace(ex);

        return ResponseEntity.badRequest().body(errorResponse);
    }

    public void showStackTrace(Exception ex)
    {
        LOGGER.error(
                """
        
                ================= EXCEPTION =================
                TYPE          : {}
                MESSAGE       : {}
                STACK_TRACE   : {}
                ============================================
        
                """,
                ex.getClass().getName(),
                ex.getMessage(),
                ex.getStackTrace()
        );
    }
}
