package com.exh.jobseeker.service;

import com.exh.jobseeker.config.JwtService;
import com.exh.jobseeker.exception.TokenNotFoundException;
import com.exh.jobseeker.exception.TokenRefreshException;
import com.exh.jobseeker.model.entity.RefreshToken;
import com.exh.jobseeker.model.entity.User;
import com.exh.jobseeker.repository.RefreshTokenRepository;
import com.exh.jobseeker.repository.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class RefreshTokenService {
    private static final int MAX_ACTIVE_SESSIONS_PER_USER = 5;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository, JwtService jwtService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Transactional
    public RefreshToken createRefreshToken(String userEmail) {
        User user = userRepository.findByEmail(userEmail);

        if (user == null) {
            throw new RuntimeException("User not found with email: " + userEmail);
        }

        List<RefreshToken> activeTokens = refreshTokenRepository.findActiveTokensByUser(user, Instant.now());
        if (activeTokens.size() >= MAX_ACTIVE_SESSIONS_PER_USER) {
            activeTokens.stream()
                    .min(Comparator.comparing(RefreshToken::getCreatedAt))
                    .ifPresent(token -> {
                        token.setRevoked(true);
                        refreshTokenRepository.save(token);
                    });
        }

        String refreshTokenString = UUID.randomUUID().toString();

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(refreshTokenString)
                .expiryDate(Instant.now().plusMillis(jwtService.getRefreshTokenExpiration()))
                .revoked(false)
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    @Transactional
    public String refreshAccessToken(String refreshToken) {
        return findByToken(refreshToken)
                .map(this::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(jwtService::generateAccessToken)
                .orElseThrow(() -> new TokenRefreshException(refreshToken, "Refresh token not found in database"));
    }

    @Transactional
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.isExpired()) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new sign in request");
        }

        if (token.isRevoked()) {
            throw new TokenRefreshException(token.getToken(), "Refresh token was revoked. Please make a new sign in request");
        }

        return token;
    }

    @Transactional
    public void revokeRefreshToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new TokenRefreshException(token, "Refresh token not found"));

        refreshToken.setRevoked(true);
        refreshTokenRepository.save(refreshToken);
    }

    @Transactional
    public void revokeTokenById(UUID tokenId) {
        RefreshToken refreshToken = refreshTokenRepository.findById(tokenId)
                .orElseThrow(() -> new TokenNotFoundException("Refresh token not found with id: " + tokenId));

        refreshToken.setRevoked(true);
        refreshTokenRepository.save(refreshToken);
    }
    @Transactional
    public void revokeAllUserTokens(User user) {
        refreshTokenRepository.revokeAllUserTokens(user);
    }

    public List<RefreshToken> getActiveTokensByUser(User user) {
        return refreshTokenRepository.findActiveTokensByUser(user, Instant.now());
    }

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void cleanupExpiredTokens() {
        refreshTokenRepository.deleteAllExpiredTokens(Instant.now());
    }
}