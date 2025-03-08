package com.exh.jobseeker.repository;

import com.exh.jobseeker.model.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SkillRepository extends JpaRepository<Skill, UUID> {
    Optional<Skill> findSkillByName(String name);
}
