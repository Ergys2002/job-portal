package com.exh.jobseeker.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "job_seeker_skill")
public class JobSeekerSkill extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "job_seeker_id", referencedColumnName = "id")
    private JobSeeker jobSeeker;
    @ManyToOne
    @JoinColumn(name = "skill_id", referencedColumnName = "id")
    private Skill skill;

}
