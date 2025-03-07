package com.exh.jobseeker.model.dto.response;

import java.time.Instant;
import java.util.UUID;

public class DeviceSessionDto {
    private UUID sessionId;
    private Instant createdAt;
    private Instant expiresAt;

    public DeviceSessionDto() {
    }

    public DeviceSessionDto(UUID sessionId, Instant createdAt, Instant expiresAt) {
        this.sessionId = sessionId;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public void setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    private static class Builder {
        private UUID sessionId;
        private Instant createdAt;
        private Instant expiresAt;

        public Builder sessionId(UUID sessionId) {
            this.sessionId = sessionId;
            return this;
        }

        public Builder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder expiresAt(Instant expiresAt) {
            this.expiresAt = expiresAt;
            return this;
        }

        public DeviceSessionDto build() {
            return new DeviceSessionDto(sessionId, createdAt, expiresAt);
        }
    }
}