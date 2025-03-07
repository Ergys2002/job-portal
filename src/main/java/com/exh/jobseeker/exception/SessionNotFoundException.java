package com.exh.jobseeker.exception;

public class SessionNotFoundException extends RuntimeException{
    public SessionNotFoundException(String message) {
        super(message);
    }
}
