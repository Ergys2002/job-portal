package com.exh.jobseeker.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CompanyCreateRequest {
    @NotBlank
    private String name;
    @Size(max = 1000, message = "Description is too long")
    private String description;
    private String website;
    private Integer numberOfEmployees;
    @NotBlank
    private String location;
    private Integer foundationYear;
    @NotBlank
    private String industry;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getWebsite() {
        return website;
    }

    public Integer getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public String getLocation() {
        return location;
    }

    public Integer getFoundationYear() {
        return foundationYear;
    }

    public String getIndustry() {
        return industry;
    }
}
