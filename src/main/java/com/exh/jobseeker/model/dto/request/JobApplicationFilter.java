package com.exh.jobseeker.model.dto.request;

import com.exh.jobseeker.model.enums.JobApplicationStatus;

import java.util.UUID;

public class JobApplicationFilter {
    private UUID jobOpeningId;
    private String jobOpeningTitle;
    private JobApplicationStatus status;

    public UUID getJobOpeningId() {
        return jobOpeningId;
    }

    public void setJobOpeningId(UUID jobOpeningId) {
        this.jobOpeningId = jobOpeningId;
    }

    public String getJobOpeningTitle() {
        return jobOpeningTitle;
    }

    public void setJobOpeningTitle(String jobOpeningTitle) {
        this.jobOpeningTitle = jobOpeningTitle;
    }

    public JobApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(JobApplicationStatus status) {
        this.status = status;
    }
}
