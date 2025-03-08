package com.exh.jobseeker.exception;

import com.exh.jobseeker.model.entity.User;

public class EmployerProfileNotCompletedException extends RuntimeException {
    public EmployerProfileNotCompletedException(User user) {
        super("Employer profile not completed for user: " + user.getId());
    }
}
