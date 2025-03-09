package com.exh.jobseeker.repository;

import com.exh.jobseeker.model.entity.JobApplication;
import com.exh.jobseeker.model.entity.JobOpening;
import com.exh.jobseeker.model.entity.JobSeeker;
import com.exh.jobseeker.model.enums.JobApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, UUID> {
    boolean existsByJobSeekerAndJobOpening(JobSeeker jobSeeker, JobOpening jobOpening);

    @Query("SELECT ja FROM JobApplication ja " +
            "WHERE ja.jobOpening.employer.id = :employerId")
    Page<JobApplication> findAllByJobOpeningEmployerId(@Param("employerId") UUID employerId, Pageable pageable);

    @Query("SELECT ja FROM JobApplication ja " +
            "WHERE ja.jobSeeker.id = :jobSeekerId")
    Page<JobApplication> findAllByJobSeekerId(@Param("jobSeekerId") UUID jobSeekerId, Pageable pageable);

    @Query(value = "SELECT DISTINCT ja FROM JobApplication ja " +
            "JOIN FETCH ja.jobSeeker js " +
            "JOIN FETCH js.user jsu " +
            "JOIN FETCH jsu.userInfo " +
            "JOIN FETCH ja.jobOpening jo " +
            "WHERE jo.employer.id = :employerId " +
            "AND (:jobOpeningId IS NULL OR jo.id = :jobOpeningId) " +
            "AND (:jobOpeningTitle IS NULL OR LOWER(jo.title) LIKE LOWER(CONCAT('%', :jobOpeningTitle, '%'))) " +
            "AND (:status IS NULL OR ja.status = :status)",
            countQuery = "SELECT COUNT(ja) FROM JobApplication ja " +
                    "JOIN ja.jobOpening jo " +
                    "WHERE jo.employer.id = :employerId " +
                    "AND (:jobOpeningId IS NULL OR jo.id = :jobOpeningId) " +
                    "AND (:jobOpeningTitle IS NULL OR LOWER(jo.title) LIKE LOWER(CONCAT('%', :jobOpeningTitle, '%'))) " +
                    "AND (:status IS NULL OR ja.status = :status)")
    Page<JobApplication> findJobApplicationsForEmployer(
            @Param("employerId") UUID employerId,
            @Param("jobOpeningId") UUID jobOpeningId,
            @Param("jobOpeningTitle") String jobOpeningTitle,
            @Param("status") JobApplicationStatus status,
            Pageable pageable);

    @Query(value = "SELECT DISTINCT ja FROM JobApplication ja " +
            "JOIN FETCH ja.jobOpening jo " +
            "JOIN FETCH jo.employer e " +
            "JOIN FETCH e.company " +
            "JOIN FETCH e.user eu " +
            "JOIN FETCH eu.userInfo " +
            "WHERE ja.jobSeeker.id = :jobSeekerId " +
            "AND (:jobOpeningTitle IS NULL OR LOWER(jo.title) LIKE LOWER(CONCAT('%', :jobOpeningTitle, '%'))) " +
            "AND (:status IS NULL OR ja.status = :status)",
            countQuery = "SELECT COUNT(ja) FROM JobApplication ja " +
                    "JOIN ja.jobOpening jo " +
                    "WHERE ja.jobSeeker.id = :jobSeekerId " +
                    "AND (:jobOpeningTitle IS NULL OR LOWER(jo.title) LIKE LOWER(CONCAT('%', :jobOpeningTitle, '%'))) " +
                    "AND (:status IS NULL OR ja.status = :status)")
    Page<JobApplication> findJobApplicationsForJobSeeker(
            @Param("jobSeekerId") UUID jobSeekerId,
            @Param("jobOpeningTitle") String jobOpeningTitle,
            @Param("status") JobApplicationStatus status,
            Pageable pageable);
}
