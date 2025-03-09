package com.exh.jobseeker.model.dto.request;

import com.exh.jobseeker.model.enums.WorkLocationType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.util.Date;
import java.util.Set;

public class JobOpeningCreateRequest {
    @NotBlank(message = "Title is required")
    private String title;

    @Size(max = 3000, message = "Description must be less than 3000 characters")
    private String description;
    @NotNull(message = "Work location type is required")
    private WorkLocationType workLocationType;
    @Min(value = 0, message = "Min salary must be greater than or equal to 0")
    private Double minSalary;
    @Min(value = 0, message = "Max salary must be greater than or equal to 0")
    private Double maxSalary;
    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Application deadline is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Future(message = "Application deadline must be in the future")
    private Date applicationDeadline;
    @Valid
    @Size(min = 1, message = "At least one skill is required")
    @Size(max = 10, message = "No more than 10 skills can be specified")
    private Set<SkillCreateRequest> skills;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public WorkLocationType getWorkLocationType() {
        return workLocationType;
    }

    public Double getMinSalary() {
        return minSalary;
    }

    public Double getMaxSalary() {
        return maxSalary;
    }

    public Date getApplicationDeadline() {
        return applicationDeadline;
    }

    public Set<SkillCreateRequest> getSkills() {
        return skills;
    }

    public String getLocation() {
        return location;
    }
}
