package com.exh.jobseeker.model.dto.response;

public class RegisterResponse {
    private String accessToken;
    private String refreshToken;
    private String tokenType;

    public RegisterResponse() {
        this.tokenType = "Bearer";
    }

    public RegisterResponse(String accessToken, String refreshToken, String tokenType) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenType = tokenType != null ? tokenType : "Bearer";
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public static RegisterResponse.Builder builder() {
        return new RegisterResponse.Builder();
    }

    public static class Builder {
        private String accessToken;
        private String refreshToken;
        private String tokenType = "Bearer";

        public RegisterResponse.Builder accessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public RegisterResponse.Builder refreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        public RegisterResponse.Builder tokenType(String tokenType) {
            this.tokenType = tokenType;
            return this;
        }

        public RegisterResponse build() {
            return new RegisterResponse(accessToken, refreshToken, tokenType);
        }
    }
}
