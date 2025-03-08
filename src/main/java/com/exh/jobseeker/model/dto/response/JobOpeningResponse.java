package com.exh.jobseeker.model.dto.response;

import com.exh.jobseeker.model.enums.JobOpeningStatus;
import com.exh.jobseeker.model.enums.WorkLocationType;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

public class JobOpeningResponse {
    private UUID id;
    private String title;
    private String description;
    private WorkLocationType workLocationType;
    private Double minSalary;
    private Double maxSalary;
    private Date applicationDeadline;
    private JobOpeningStatus status;
    private Set<SkillResponse> skills;

    public JobOpeningResponse(
            UUID id,
            String title,
            String description,
            WorkLocationType workLocationType,
            Double minSalary, Double maxSalary,
            Date applicationDeadline,
            JobOpeningStatus status,
            Set<SkillResponse> skills
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.workLocationType = workLocationType;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        this.applicationDeadline = applicationDeadline;
        this.status = status;
        this.skills = skills;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public WorkLocationType getWorkLocationType() {
        return workLocationType;
    }

    public void setWorkLocationType(WorkLocationType workLocationType) {
        this.workLocationType = workLocationType;
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

    public Date getApplicationDeadline() {
        return applicationDeadline;
    }

    public void setApplicationDeadline(Date applicationDeadline) {
        this.applicationDeadline = applicationDeadline;
    }

    public JobOpeningStatus getStatus() {
        return status;
    }

    public void setStatus(JobOpeningStatus status) {
        this.status = status;
    }

    public Set<SkillResponse> getSkills() {
        return skills;
    }

    public void setSkills(Set<SkillResponse> skills) {
        this.skills = skills;
    }
}
