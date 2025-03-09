package com.exh.jobseeker.model.dto.request;

import com.exh.jobseeker.model.enums.JobApplicationStatus;

public class JobApplicationUpdateRequest {
    private JobApplicationStatus status;
    private ReviewCreateRequest review;

    public JobApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(JobApplicationStatus status) {
        this.status = status;
    }

    public ReviewCreateRequest getReview() {
        return review;
    }

    public void setReview(ReviewCreateRequest review) {
        this.review = review;
    }
}
