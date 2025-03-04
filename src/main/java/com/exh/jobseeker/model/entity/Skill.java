package com.exh.jobseeker.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.Set;

@Entity
@DynamicUpdate
@SQLDelete(sql = "UPDATE skill SET deleted = true, deleted_at = now() WHERE id = ?")
@SQLRestriction("deleted = false")
@Table(name = "skill")
public class Skill extends BaseEntity{
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "skill")
    private Set<JobSeekerSkill> jobSeekerSkills;
    @OneToMany(mappedBy = "skill")
    private Set <JobOpeningSkill> jobOpeningSkills;
}
