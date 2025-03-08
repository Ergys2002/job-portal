package com.exh.jobseeker.exception;

import com.exh.jobseeker.model.entity.User;

public class EmployerAlreadyExistsForUserException extends RuntimeException {
    public EmployerAlreadyExistsForUserException(User user) {
        super("Employer related data already exists for user with id: " + user.getId());
    }
}
