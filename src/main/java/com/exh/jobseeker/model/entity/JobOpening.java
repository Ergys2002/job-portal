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
    @Column(name = "min_salary")
    private Double minSalary;
    @Column(name = "max_salary")
    private Double maxSalary;
    @Column(name = "application_deadline", nullable = false)
    private SimpleDateFormat applicationDeadline;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private JobOpeningStatus status;
    @OneToMany(mappedBy = "jobOpening")
    private Set<JobOpeningSkill> jobOpeningSkills;

    @ManyToOne
    @JoinColumn(name = "employer_id", nullable = false)
    private Employer employer;
}
