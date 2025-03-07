package com.exh.jobseeker.model.dto.response;

public class RegisterResponse {
    private String accessToken;
    private String refreshToken;

    public RegisterResponse() {

    }

    public RegisterResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
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

    public static RegisterResponse.Builder builder() {
        return new RegisterResponse.Builder();
    }

    public static class Builder {
        private String accessToken;
        private String refreshToken;

        public RegisterResponse.Builder accessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public RegisterResponse.Builder refreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        public RegisterResponse build() {
            return new RegisterResponse(accessToken, refreshToken);
        }
    }
}
