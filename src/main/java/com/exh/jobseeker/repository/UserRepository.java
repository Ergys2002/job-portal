package com.exh.jobseeker.repository;

import com.exh.jobseeker.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByEmail(String email);
    boolean existsByEmail(String email);
}
