package com.exh.jobseeker.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "job_application_review")
@DynamicUpdate
@SQLDelete(sql = "UPDATE job_application_review SET deleted = true, deleted_at = now() WHERE id = ?")
@SQLRestriction("deleted = false")
public class JobApplicationReview extends BaseEntity{
    @Column(name = "rating", nullable = false)
    private Integer rating;
    @Column(name = "comment", nullable = false)
    private String comment;
    @OneToOne(mappedBy = "jobApplicationReview")
    private JobApplication jobApplication;

    public JobApplicationReview() {
    }

    public JobApplicationReview(Integer rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public JobApplication getJobApplication() {
        return jobApplication;
    }

    public void setJobApplication(JobApplication jobApplication) {
        this.jobApplication = jobApplication;
    }
}
