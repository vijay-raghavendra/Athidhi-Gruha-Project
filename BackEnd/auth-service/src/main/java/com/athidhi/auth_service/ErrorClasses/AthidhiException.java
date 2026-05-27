package com.athidhi.auth_service.ErrorClasses;

public class AthidhiException extends Exception {

    public AthidhiException(String message) {

        super(message);
    }
    public AthidhiException(String message, Throwable cause) {

        super(message, cause);
    }
    public AthidhiException(Throwable cause) {

        super(cause);
    }

}
