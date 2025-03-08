package com.exh.jobseeker.model.entity;

import com.exh.jobseeker.model.enums.JobOpeningStatus;
import com.exh.jobseeker.model.enums.WorkLocationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "job_opening")
@DynamicUpdate
@SQLDelete(sql = "UPDATE job_opening SET deleted = true, deleted_at = now() WHERE id = ?")
@SQLRestriction("deleted = false")
public class JobOpening extends BaseEntity{
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "work_location_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private WorkLocationType workLocationType;
    @Column(name = "location", nullable = false)
    private String location;
    @Column(name = "min_salary")
    private Double minSalary;
    @Column(name = "max_salary")
    private Double maxSalary;
    @Column(name = "application_deadline", nullable = false)
    private Date applicationDeadline;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private JobOpeningStatus status;
    @OneToMany(mappedBy = "jobOpening")
    private Set<JobOpeningSkill> jobOpeningSkills;

    @ManyToOne
    @JoinColumn(name = "employer_id", nullable = false)
    private Employer employer;

    public JobOpening() {
    }

    public JobOpening(
            String title,
            String description,
            WorkLocationType workLocationType,
            String location, Double minSalary,
            Double maxSalary,
            Date applicationDeadline,
            JobOpeningStatus status,
            Set<JobOpeningSkill> jobOpeningSkills,
            Employer employer
    ) {
        this.title = title;
        this.description = description;
        this.workLocationType = workLocationType;
        this.location = location;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        this.applicationDeadline = applicationDeadline;
        this.status = status;
        this.jobOpeningSkills = jobOpeningSkills;
        this.employer = employer;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public Set<JobOpeningSkill> getJobOpeningSkills() {
        return jobOpeningSkills;
    }

    public void setJobOpeningSkills(Set<JobOpeningSkill> jobOpeningSkills) {
        this.jobOpeningSkills = jobOpeningSkills;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }
}
