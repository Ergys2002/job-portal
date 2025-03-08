package com.exh.jobseeker.repository;

import com.exh.jobseeker.model.entity.Employer;
import com.exh.jobseeker.model.entity.JobOpening;
import com.exh.jobseeker.model.enums.JobOpeningStatus;
import com.exh.jobseeker.model.enums.WorkLocationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JobOpeningRepository extends JpaRepository<JobOpening, UUID> {
    @Query("SELECT j FROM JobOpening j WHERE j.employer = :employer " +
            "AND (:status IS NULL OR j.status = :status) " +
            "AND (:title IS NULL OR LOWER(j.title) LIKE LOWER(CONCAT('%', :title, '%')))")
    Page<JobOpening> findJobOpeningsFiltered(
            @Param("employer") Employer employer,
            @Param("status") JobOpeningStatus status,
            @Param("title") String title,
            Pageable pageable
    );

    @Query("SELECT j FROM JobOpening j WHERE j.status = 'ACTIVE' " +
            "AND (:title IS NULL OR LOWER(j.title) LIKE LOWER(CONCAT('%', :title, '%'))) " +
            "AND (:preferredSalary IS NULL OR " +
            "(j.minSalary <= :preferredSalary AND j.maxSalary >= :preferredSalary)) " +
            "AND (:workLocationType IS NULL OR j.workLocationType = :workLocationType) " +
            "AND (:location IS NULL OR LOWER(j.location) LIKE LOWER(CONCAT('%', :location, '%')))")
    Page<JobOpening> findJobOpeningsWithFilters(
            @Param("title") String title,
            @Param("preferredSalary") Double preferredSalary,
            @Param("workLocationType") WorkLocationType workLocationType,
            @Param("location") String location,
            Pageable pageable);

    @Query("SELECT DISTINCT j FROM JobOpening j " +
            "JOIN j.jobOpeningSkills jos " +
            "JOIN jos.skill s " +
            "JOIN s.jobSeekerSkills jss " +
            "JOIN jss.jobSeeker js " +
            "WHERE js.user.id = :userId AND j.status = 'ACTIVE'")
    Page<JobOpening> findJobOpeningsByJobSeekerSkills(
            @Param("userId") UUID userId,
            Pageable pageable);

    @Query(value = "SELECT * FROM job_opening " +
            "WHERE status = 'ACTIVE' AND deleted = false " +
            "ORDER BY RAND() LIMIT :limit",
            nativeQuery = true)
    List<JobOpening> findRandomActiveJobOpenings(@Param("limit") int limit);

}


