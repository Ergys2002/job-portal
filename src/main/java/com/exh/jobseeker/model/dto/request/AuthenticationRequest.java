package com.exh.jobseeker.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class AuthenticationRequest {
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email should not be blank")
    private String email;
    @NotBlank(message = "Password should not be blank")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!?_\\-\\[\\]{}()<>,.;:'\\\"\\/\\\\|]).*$",
            message = "Password must contain at least one letter, one digit, and one special character")
    private String password;

    public AuthenticationRequest() {
    }

    public AuthenticationRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
