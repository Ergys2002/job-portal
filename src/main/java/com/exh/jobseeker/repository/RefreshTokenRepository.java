package com.exh.jobseeker.repository;

import com.exh.jobseeker.model.entity.RefreshToken;
import com.exh.jobseeker.model.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    @Modifying
    @Transactional
    @Query("DELETE FROM RefreshToken rt WHERE rt.user = ?1")
    void deleteByUser(User user);

    @Modifying
    @Transactional
    @Query("UPDATE RefreshToken rt SET rt.revoked = true WHERE rt.user = ?1 AND rt.revoked = false")
    void revokeAllUserTokens(User user);

    @Modifying
    @Transactional
    @Query("DELETE FROM RefreshToken rt WHERE rt.expiryDate < ?1")
    void deleteAllExpiredTokens(Instant now);

    List<RefreshToken> findAllByUserAndRevokedFalse(User user);
}