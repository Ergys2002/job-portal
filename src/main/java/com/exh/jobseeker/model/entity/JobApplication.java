package com.exh.jobseeker.model.entity;

import com.exh.jobseeker.model.enums.JobApplicationStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "job_application")
@DynamicUpdate
@SQLDelete(sql = "UPDATE job_application SET deleted = true, deleted_at = now() WHERE id = ?")
@SQLRestriction("deleted = false")
public class JobApplication extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "job_opening_id", nullable = false)
    private JobOpening jobOpening;
    @ManyToOne
    @JoinColumn(name = "job_seeker_id", nullable = false)
    private JobSeeker jobSeeker;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private JobApplicationStatus status;
    @OneToOne
    @JoinColumn(name = "job_application_review_id", referencedColumnName = "id")
    private JobApplicationReview jobApplicationReview;

    public JobApplication() {
    }

    public JobApplication(JobOpening jobOpening, JobSeeker jobSeeker, JobApplicationStatus status) {
        this.jobOpening = jobOpening;
        this.jobSeeker = jobSeeker;
        this.status = status;
    }

    public JobOpening getJobOpening() {
        return jobOpening;
    }

    public void setJobOpening(JobOpening jobOpening) {
        this.jobOpening = jobOpening;
    }

    public JobSeeker getJobSeeker() {
        return jobSeeker;
    }

    public void setJobSeeker(JobSeeker jobSeeker) {
        this.jobSeeker = jobSeeker;
    }

    public JobApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(JobApplicationStatus status) {
        this.status = status;
    }

    public JobApplicationReview getJobApplicationReview() {
        return jobApplicationReview;
    }

    public void setJobApplicationReview(JobApplicationReview jobApplicationReview) {
        this.jobApplicationReview = jobApplicationReview;
    }
}
