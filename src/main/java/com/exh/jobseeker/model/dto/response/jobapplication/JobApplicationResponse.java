package com.exh.jobseeker.model.dto.response.jobapplication;

import com.exh.jobseeker.model.enums.JobApplicationStatus;
import java.time.Instant;
import java.util.UUID;

public class JobApplicationResponse {
    private UUID id;
    private UUID jobOpeningId;
    private String jobOpeningTitle;
    private JobApplicationStatus status;

    // common fields
    private Instant appliedAt;
    private Instant lastUpdatedAt;
    private String jobDescription;
    private Double minSalary;
    private Double maxSalary;
    private String workLocationType;
    private String location;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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

    public Instant getAppliedAt() {
        return appliedAt;
    }

    public void setAppliedAt(Instant appliedAt) {
        this.appliedAt = appliedAt;
    }

    public Instant getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(Instant lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public Double getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(Double minSalary) {
        this.minSalary = minSalary;
    }

    public Double getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(Double maxSalary) {
        this.maxSalary = maxSalary;
    }

    public String getWorkLocationType() {
        return workLocationType;
    }

    public void setWorkLocationType(String workLocationType) {
        this.workLocationType = workLocationType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}