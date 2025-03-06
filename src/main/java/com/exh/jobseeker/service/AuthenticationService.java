package com.exh.jobseeker.service;

import com.exh.jobseeker.config.JwtService;
import com.exh.jobseeker.exception.EmailAlreadyExistsException;
import com.exh.jobseeker.exception.PhoneNumberAlreadyExistsException;
import com.exh.jobseeker.exception.TokenRefreshException;
import com.exh.jobseeker.model.dto.request.AuthenticationRequest;
import com.exh.jobseeker.model.dto.request.RegisterRequest;
import com.exh.jobseeker.model.dto.request.TokenRefreshRequest;
import com.exh.jobseeker.model.dto.response.AuthenticationResponse;
import com.exh.jobseeker.model.dto.response.RegisterResponse;
import com.exh.jobseeker.model.dto.response.TokenRefreshResponse;
import com.exh.jobseeker.model.entity.RefreshToken;
import com.exh.jobseeker.model.entity.User;
import com.exh.jobseeker.model.entity.UserInfo;
import com.exh.jobseeker.model.enums.Role;
import com.exh.jobseeker.repository.UserInfoRepository;
import com.exh.jobseeker.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final UserInfoRepository userInfoRepository;

    public AuthenticationService(UserRepository userRepository, JwtService jwtService, AuthenticationManager authenticationManager, RefreshTokenService refreshTokenService, UserInfoRepository userInfoRepository) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService;
        this.userInfoRepository = userInfoRepository;
    }

    //TODO: Handle failed login attempt
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

        User user = buildJobSeeker(request);

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
                    return TokenRefreshResponse.builder()
                            .refreshToken(requestRefreshToken)
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

    private UserDetails authenticateAndGetUserDetails(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return (UserDetails) authentication.getPrincipal();
    }

    private User buildJobSeeker(RegisterRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(Role.JOB_SEEKER);
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
