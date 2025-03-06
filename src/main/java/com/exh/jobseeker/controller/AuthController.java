package com.exh.jobseeker.controller;

import com.exh.jobseeker.model.dto.request.AuthenticationRequest;
import com.exh.jobseeker.model.dto.request.RegisterRequest;
import com.exh.jobseeker.model.dto.request.TokenRefreshRequest;
import com.exh.jobseeker.model.dto.response.AuthenticationResponse;
import com.exh.jobseeker.model.dto.response.RegisterResponse;
import com.exh.jobseeker.model.dto.response.TokenRefreshResponse;
import com.exh.jobseeker.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationService authService;

    public AuthController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticateUser(@Valid @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerJobSeeker(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenRefreshResponse> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
        return ResponseEntity.ok(authService.refreshToken(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@Valid @RequestBody TokenRefreshRequest request) {
        authService.logout(request.getRefreshToken());
        return ResponseEntity.ok("Logout successful");
    }

    @PostMapping("/logout-all")
    public ResponseEntity<?> logoutAllDevices() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            authService.logoutAll(userDetails.getUsername());
            return ResponseEntity.ok("Logged out from all devices");
        }

        return ResponseEntity.badRequest().body("Not authenticated");
    }
}
