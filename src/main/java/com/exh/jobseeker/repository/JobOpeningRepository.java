package com.exh.jobseeker.repository;

import com.exh.jobseeker.model.entity.JobOpening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JobOpeningRepository extends JpaRepository<JobOpening, UUID> {
}
