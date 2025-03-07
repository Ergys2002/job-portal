package com.exh.jobseeker.repository;

import com.exh.jobseeker.model.entity.RefreshToken;
import com.exh.jobseeker.model.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    Optional<RefreshToken> findByToken(String token);

    List<RefreshToken> findAllByUser(User user);

    @Modifying
    @Query("UPDATE RefreshToken rt SET rt.revoked = true WHERE rt.user = :user AND rt.revoked = false")
    void revokeAllUserTokens(@Param("user") User user);

    @Modifying
    @Query("DELETE FROM RefreshToken rt WHERE rt.expiryDate < :now")
    void deleteAllExpiredTokens(@Param("now") Instant now);

    @Query("SELECT r FROM RefreshToken r WHERE r.user = :user AND r.revoked = false AND r.expiryDate > :now")
    List<RefreshToken> findActiveTokensByUser(@Param("user") User user, @Param("now") Instant now);

    @Query("SELECT COUNT(r) FROM RefreshToken r WHERE r.user = :user AND r.revoked = false AND r.expiryDate > :now")
    long countActiveTokensByUser(@Param("user") User user, @Param("now") Instant now);
}
