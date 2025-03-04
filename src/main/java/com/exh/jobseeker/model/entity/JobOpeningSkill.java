package com.exh.jobseeker.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "job_opening_skill")
public class JobOpeningSkill extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "job_opening_id", referencedColumnName = "id")
    private JobOpening jobOpening;
    @ManyToOne
    @JoinColumn(name = "skill_id", referencedColumnName = "id")
    private Skill skill;
}
