package com.exh.jobseeker.model.dto.response.jobapplication;

import java.util.UUID;

public class JobSeekerApplicationResponse extends JobApplicationResponse {
    // employer info
    private UUID employerId;
    private String employerPosition;
    private String employerFirstName;
    private String employerLastName;
    private String employerEmail;
    private String employerPhoneNumber;

    // company info
    private UUID companyId;
    private String companyName;
    private String companyWebsite;
    private String companyLocation;
    private String companyIndustry;
    private Integer companyNumberOfEmployees;
    private Integer companyFoundationYear;
    private String companyDescription;

    public UUID getEmployerId() {
        return employerId;
    }

    public void setEmployerId(UUID employerId) {
        this.employerId = employerId;
    }

    public String getEmployerPosition() {
        return employerPosition;
    }

    public void setEmployerPosition(String employerPosition) {
        this.employerPosition = employerPosition;
    }

    public UUID getCompanyId() {
        return companyId;
    }

    public void setCompanyId(UUID companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getCompanyNumberOfEmployees() {
        return companyNumberOfEmployees;
    }

    public void setCompanyNumberOfEmployees(Integer companyNumberOfEmployees) {
        this.companyNumberOfEmployees = companyNumberOfEmployees;
    }

    public Integer getCompanyFoundationYear() {
        return companyFoundationYear;
    }

    public void setCompanyFoundationYear(Integer companyFoundationYear) {
        this.companyFoundationYear = companyFoundationYear;
    }

    public String getCompanyWebsite() {
        return companyWebsite;
    }

    public void setCompanyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite;
    }

    public String getCompanyLocation() {
        return companyLocation;
    }

    public void setCompanyLocation(String companyLocation) {
        this.companyLocation = companyLocation;
    }

    public String getCompanyIndustry() {
        return companyIndustry;
    }

    public void setCompanyIndustry(String companyIndustry) {
        this.companyIndustry = companyIndustry;
    }



    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }

    public String getEmployerFirstName() {
        return employerFirstName;
    }

    public void setEmployerFirstName(String employerFirstName) {
        this.employerFirstName = employerFirstName;
    }

    public String getEmployerLastName() {
        return employerLastName;
    }

    public void setEmployerLastName(String employerLastName) {
        this.employerLastName = employerLastName;
    }

    public String getEmployerEmail() {
        return employerEmail;
    }

    public void setEmployerEmail(String employerEmail) {
        this.employerEmail = employerEmail;
    }

    public String getEmployerPhoneNumber() {
        return employerPhoneNumber;
    }

    public void setEmployerPhoneNumber(String employerPhoneNumber) {
        this.employerPhoneNumber = employerPhoneNumber;
    }
}