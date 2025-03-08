package com.exh.jobseeker.repository;

import com.exh.jobseeker.model.entity.User;
import com.exh.jobseeker.model.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByEmail(String email);

    boolean existsByEmail(String email);
//    Optional<User> findById(UUID id);

    @Query("SELECT u FROM User u WHERE (:role IS NULL OR u.role = :role) AND (:email IS NULL OR u.email LIKE %:email%) AND u.role != 'ADMIN'")
    Page<User> findUsersByFilters(@Param("role") Role role, @Param("email") String email, Pageable pageable);
}
