package org.example.core.error;

public class NotRetryableException extends RuntimeException {
    public NotRetryableException(String message) {
        super(message);
    }

    public NotRetryableException(Exception cause) {
        super(cause);
    }


}
