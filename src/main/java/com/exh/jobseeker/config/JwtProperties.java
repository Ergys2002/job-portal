package com.exh.jobseeker.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.jwt")
public class JwtProperties {

    private TokenProperties accessToken = new TokenProperties();
    private TokenProperties refreshToken = new TokenProperties();

    public TokenProperties getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(TokenProperties accessToken) {
        this.accessToken = accessToken;
    }

    public TokenProperties getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(TokenProperties refreshToken) {
        this.refreshToken = refreshToken;
    }

    public static class TokenProperties {
        private String secret;
        private long expirationMs;

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public long getExpirationMs() {
            return expirationMs;
        }

        public void setExpirationMs(long expirationMs) {
            this.expirationMs = expirationMs;
        }
    }
}
