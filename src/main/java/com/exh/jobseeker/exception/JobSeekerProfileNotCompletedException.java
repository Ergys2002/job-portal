package com.exh.jobseeker.exception;

import com.exh.jobseeker.model.entity.User;

public class JobSeekerProfileNotCompletedException extends RuntimeException {
    public JobSeekerProfileNotCompletedException(User user) {
        super("Job seeker profile not completed for user: " + user.getId());
    }
}
