package com.exh.jobseeker.model.entity;

import com.exh.jobseeker.model.enums.JobApplicationStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
}
