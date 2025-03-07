package com.exh.jobseeker.exception;

public class NoAuthenticatedUserException extends RuntimeException {
    public NoAuthenticatedUserException() {
        super("No authenticated user found");
    }
}
