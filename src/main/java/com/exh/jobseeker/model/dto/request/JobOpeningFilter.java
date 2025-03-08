package com.exh.jobseeker.model.dto.request;

public class JobOpeningFilter {
    private String title;
    private Double preferredSalary;
    private String workLocationType;
    private String location;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPreferredSalary() {
        return preferredSalary;
    }

    public void setPreferredSalary(Double preferredSalary) {
        this.preferredSalary = preferredSalary;
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
