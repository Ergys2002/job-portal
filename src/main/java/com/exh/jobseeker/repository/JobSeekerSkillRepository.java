package com.exh.jobseeker.repository;

import com.exh.jobseeker.model.entity.JobSeeker;
import com.exh.jobseeker.model.entity.JobSeekerSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JobSeekerSkillRepository extends JpaRepository<JobSeekerSkill, UUID> {
    List<JobSeekerSkill> findByJobSeeker(JobSeeker jobSeeker);
}
