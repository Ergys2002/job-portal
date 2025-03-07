package com.exh.jobseeker.service;

import com.exh.jobseeker.config.JwtService;
import com.exh.jobseeker.exception.AccessDeniedException;
import com.exh.jobseeker.exception.EmailAlreadyExistsException;
import com.exh.jobseeker.exception.InvalidCredentialsException;
import com.exh.jobseeker.exception.PhoneNumberAlreadyExistsException;
import com.exh.jobseeker.exception.SessionNotFoundException;
import com.exh.jobseeker.exception.TokenRefreshException;
import com.exh.jobseeker.model.dto.request.AuthenticationRequest;
import com.exh.jobseeker.model.dto.request.RegisterRequest;
import com.exh.jobseeker.model.dto.request.TokenRefreshRequest;
import com.exh.jobseeker.model.dto.response.AuthenticationResponse;
import com.exh.jobseeker.model.dto.response.DeviceSessionDto;
import com.exh.jobseeker.model.dto.response.RegisterResponse;
import com.exh.jobseeker.model.dto.response.TokenRefreshResponse;
import com.exh.jobseeker.model.entity.RefreshToken;
import com.exh.jobseeker.model.entity.User;
import com.exh.jobseeker.model.entity.UserInfo;
import com.exh.jobseeker.model.enums.Role;
import com.exh.jobseeker.repository.RefreshTokenRepository;
import com.exh.jobseeker.repository.UserInfoRepository;
import com.exh.jobseeker.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;

    public AuthenticationService(
            UserRepository userRepository,
            JwtService jwtService,
            AuthenticationManager authenticationManager,
            RefreshTokenService refreshTokenService,
            UserInfoRepository userInfoRepository,
            PasswordEncoder passwordEncoder,
            RefreshTokenRepository refreshTokenRepository
    ) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService;
        this.userInfoRepository = userInfoRepository;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Transactional
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        UserDetails userDetails = authenticateAndGetUserDetails(request.getEmail(), request.getPassword());

        String accessToken = jwtService.generateAccessToken(userDetails);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername());

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }

    @Transactional
    public RegisterResponse register(RegisterRequest request) {

        checkEmailAndPhoneNumber(request);

        User user = buildUser(request);

        User savedUser = userRepository.save(user);

        UserInfo userInfo = buildUserInfo(request, savedUser);

        userInfoRepository.save(userInfo);

        UserDetails userDetails = authenticateAndGetUserDetails(request.getEmail(), request.getPassword());

        String accessToken = jwtService.generateAccessToken(userDetails);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername());

        return RegisterResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .build();

    }

    @Transactional
    public TokenRefreshResponse refreshToken(TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String newAccessToken = jwtService.generateAccessToken(user);

                    refreshTokenService.revokeRefreshToken(requestRefreshToken);
                    RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(user.getEmail());

                    return TokenRefreshResponse.builder()
                            .refreshToken(newRefreshToken.getToken())
                            .accessToken(newAccessToken)
                            .build();
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Refresh token not found in database"));
    }

    @Transactional
    public void logout(String refreshToken) {
        refreshTokenService.revokeRefreshToken(refreshToken);
    }

    @Transactional
    public void logoutAll(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            refreshTokenService.revokeAllUserTokens(user);
        }
    }

    @Transactional(readOnly = true)
    public List<DeviceSessionDto> getUserActiveSessions(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        List<RefreshToken> activeSessions = refreshTokenRepository.findActiveTokensByUser(user, Instant.now());
        return activeSessions.stream()
                .map(token -> new DeviceSessionDto(
                        token.getId(),
                        token.getCreatedAt(),
                        token.getExpiryDate()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public void terminateSession(String email, UUID sessionId) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        RefreshToken token = refreshTokenRepository.findById(sessionId)
                .orElseThrow(() -> new SessionNotFoundException("Session not found"));

        if (!token.getUser().equals(user)) {
            throw new AccessDeniedException("You don't have permission to terminate this session");
        }

        refreshTokenService.revokeTokenById(sessionId);
    }
    private UserDetails authenticateAndGetUserDetails(String email, String password) {
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            return (UserDetails) authentication.getPrincipal();
        } catch (AuthenticationException e){
            throw new InvalidCredentialsException();
        }
    }

    private User buildUser(RegisterRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        if (request.getRole() != null) {
            user.setRole(request.getRole());
        } else {
            user.setRole(Role.JOB_SEEKER);
        }

        return user;
    }

    private UserInfo buildUserInfo(RegisterRequest request, User user) {
        UserInfo userInfo = new UserInfo();
        userInfo.setFirstName(request.getFirstName());
        userInfo.setLastName(request.getLastName());
        userInfo.setPhoneNumber(request.getPhoneNumber());
        userInfo.setBirthDate(request.getBirthDate());
        userInfo.setGender(request.getGender());
        userInfo.setUser(user);
        return userInfo;
    }
    private void checkEmailExists(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException(email);
        }
    }

    private void checkPhoneNumberExists(String phoneNumber) {
        if (userInfoRepository.existsByPhoneNumber(phoneNumber)) {
            throw new PhoneNumberAlreadyExistsException(phoneNumber);
        }
    }

    private void checkEmailAndPhoneNumber(RegisterRequest request) {
        checkEmailExists(request.getEmail());
        checkPhoneNumberExists(request.getPhoneNumber());
    }


}
