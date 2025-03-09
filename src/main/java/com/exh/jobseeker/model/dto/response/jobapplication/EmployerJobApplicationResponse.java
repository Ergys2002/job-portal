package com.exh.jobseeker.model.dto.response.jobapplication;

import com.exh.jobseeker.model.enums.EducationLevel;
import com.exh.jobseeker.model.enums.Gender;
import java.util.UUID;

public class EmployerJobApplicationResponse extends JobApplicationResponse {
    private UUID jobSeekerId;
    private String cvUrl;
    private Integer experienceYears;
    private EducationLevel educationLevel;
    private String headline;
    private String summary;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Gender gender;

    public UUID getJobSeekerId() {
        return jobSeekerId;
    }

    public void setJobSeekerId(UUID jobSeekerId) {
        this.jobSeekerId = jobSeekerId;
    }

    public String getCvUrl() {
        return cvUrl;
    }

    public void setCvUrl(String cvUrl) {
        this.cvUrl = cvUrl;
    }

    public Integer getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(Integer experienceYears) {
        this.experienceYears = experienceYears;
    }

    public EducationLevel getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(EducationLevel educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}