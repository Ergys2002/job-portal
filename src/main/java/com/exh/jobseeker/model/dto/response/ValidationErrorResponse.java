package com.exh.jobseeker.model.dto.response;

import java.time.Instant;
import java.util.Map;

public class ValidationErrorResponse extends ErrorResponse {
    private Map<String, String> validationErrors;

    public ValidationErrorResponse() {
    }

    public Map<String, String> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(Map<String, String> validationErrors) {
        this.validationErrors = validationErrors;
    }

    public static ValidationErrorResponseBuilder validationBuilder() {
        return new ValidationErrorResponseBuilder();
    }

    public static class ValidationErrorResponseBuilder {
        private final ValidationErrorResponse validationErrorResponse;

        public ValidationErrorResponseBuilder() {
            this.validationErrorResponse = new ValidationErrorResponse();
        }

        public ValidationErrorResponseBuilder message(String message) {
            this.validationErrorResponse.setMessage(message);
            return this;
        }

        public ValidationErrorResponseBuilder status(int status) {
            this.validationErrorResponse.setStatus(status);
            return this;
        }

        public ValidationErrorResponseBuilder timestamp(Instant timestamp) {
            this.validationErrorResponse.setTimestamp(timestamp);
            return this;
        }

        public ValidationErrorResponseBuilder path(String path) {
            this.validationErrorResponse.setPath(path);
            return this;
        }

        public ValidationErrorResponseBuilder validationErrors(Map<String, String> validationErrors) {
            this.validationErrorResponse.setValidationErrors(validationErrors);
            return this;
        }

        public ValidationErrorResponse build() {
            return this.validationErrorResponse;
        }
    }
}