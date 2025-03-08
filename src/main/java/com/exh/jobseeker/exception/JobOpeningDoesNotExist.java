package com.exh.jobseeker.exception;

import java.util.UUID;

public class JobOpeningDoesNotExist extends RuntimeException {
    public JobOpeningDoesNotExist(String message) {
        super("Job opening with id " + message + " does not exist");
    }
}
