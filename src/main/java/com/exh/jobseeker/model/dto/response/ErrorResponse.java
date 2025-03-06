package com.exh.jobseeker.model.dto.response;

import java.time.Instant;

public class ErrorResponse {
    private String message;
    private int status;
    private Instant timestamp;
    private String path;

    public ErrorResponse() {
        this.timestamp = Instant.now();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final ErrorResponse errorResponse;

        public Builder() {
            this.errorResponse = new ErrorResponse();
        }

        public Builder message(String message) {
            this.errorResponse.setMessage(message);
            return this;
        }

        public Builder status(int status) {
            this.errorResponse.setStatus(status);
            return this;
        }

        public Builder timestamp(Instant timestamp) {
            this.errorResponse.setTimestamp(timestamp);
            return this;
        }

        public Builder path(String path) {
            this.errorResponse.setPath(path);
            return this;
        }

        public ErrorResponse build() {
            return this.errorResponse;
        }
    }
}