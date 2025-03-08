package com.exh.jobseeker.model.dto.request;

import com.exh.jobseeker.annotation.FileSize;
import com.exh.jobseeker.annotation.ValidFileType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Set;

public class JobSeekerCreateRequest {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @NotNull(message = "CV file is required")
    @ValidFileType(types = {"application/pdf", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            message = "Only PDF and DOCX files are allowed")
    @FileSize(max = "10MB", message = "File size must be less than 10MB")
    private MultipartFile cv;

    @Min(value = 0, message = "Experience years must be greater than or equal to 0")
    @Max(value = 60, message = "Experience years must be less than or equal to 60")
    private Integer experienceYears;

    @NotNull(message = "Education level is required")
    private String educationLevel;

    @Size(max = 255, message = "Headline must be less than 255 characters")
    private String headline;

    @Size(max = 1000, message = "Summary must be less than 1000 characters")
    private String summary;

    @Valid
    @Size(min = 1, message = "At least one skill is required")
    @Size(max = 10, message = "No more than 10 skills can be specified")
    private Set<SkillCreateRequest> skills = new HashSet<>();


    public JobSeekerCreateRequest() {
    }

    public JobSeekerCreateRequest(MultipartFile cv, Integer experienceYears, String educationLevel, String headline, String summary, Set<SkillCreateRequest> skills) {
        this.cv = cv;
        this.experienceYears = experienceYears;
        this.educationLevel = educationLevel;
        this.headline = headline;
        this.summary = summary;
        this.skills = skills;
    }

    public MultipartFile getCv() {
        return cv;
    }

    public Integer getExperienceYears() {
        return experienceYears;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public String getHeadline() {
        return headline;
    }

    public String getSummary() {
        return summary;
    }

    public Set<SkillCreateRequest> getSkills() {
        return skills;
    }

    public void setSkills(Set<SkillCreateRequest> skills) {
        this.skills = skills;
    }

    public void setCv(MultipartFile cv) {
        this.cv = cv;
    }

    public void setExperienceYears(Integer experienceYears) {
        this.experienceYears = experienceYears;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

}