package com.exh.jobseeker.model.entity;

import com.exh.jobseeker.model.enums.EducationLevel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.Set;

@Entity
@Table(name = "job_seeker")
@DynamicUpdate
@SQLDelete(sql = "UPDATE job_seeker SET deleted = true, deleted_at = now() WHERE id = ?")
@SQLRestriction("deleted = false")
public class JobSeeker extends BaseEntity{
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @Column(name = "cv_url")
    private String cvUrl;
    @Column(name = "experience_years")
    private Integer experienceYears;
    @Column(name = "education_level")
    @Enumerated(EnumType.STRING)
    private EducationLevel educationLevel;
    @Column(name = "headline")
    private String headline;
    @Column(name = "summary")
    private String summary;
    @OneToMany(mappedBy = "jobSeeker")
    private Set<JobSeekerSkill> skills;

    public JobSeeker() {
    }

    public JobSeeker(User user, String cvUrl, Integer experienceYears, EducationLevel educationLevel, String headline, String summary) {
        this.user = user;
        this.cvUrl = cvUrl;
        this.experienceYears = experienceYears;
        this.educationLevel = educationLevel;
        this.headline = headline;
        this.summary = summary;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Set<JobSeekerSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<JobSeekerSkill> skills) {
        this.skills = skills;
    }
}
