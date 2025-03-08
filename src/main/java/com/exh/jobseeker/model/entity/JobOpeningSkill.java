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

    public JobOpeningSkill() {
    }

    public JobOpeningSkill(JobOpening jobOpening, Skill skill) {
        this.jobOpening = jobOpening;
        this.skill = skill;
    }

    public JobOpening getJobOpening() {
        return jobOpening;
    }

    public void setJobOpening(JobOpening jobOpening) {
        this.jobOpening = jobOpening;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }
}
