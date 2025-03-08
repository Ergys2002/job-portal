package com.exh.jobseeker.service;

import com.exh.jobseeker.exception.NoAuthenticatedUserException;
import com.exh.jobseeker.mapper.UserMapper;
import com.exh.jobseeker.model.dto.response.UserResponse;
import com.exh.jobseeker.model.entity.User;
import com.exh.jobseeker.model.enums.Role;
import com.exh.jobseeker.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new NoAuthenticatedUserException();
        }

        String email = authentication.getName();
        return userRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public Page<UserResponse> getAllUsers(String role, String email, PageRequest pageRequest) {
        Role parsedRole = null;
        if (role != null && !role.isEmpty()) {
            try {
                parsedRole = Role.valueOf(role.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid role parameter");
            }
        }

        Page<User> users = userRepository.findUsersByFilters(parsedRole, email, pageRequest);

        return users.map(userMapper::userToUserResponse);
    }

    @Transactional
    public void deleteUser(UUID id) {
        userRepository.findById(id).ifPresent(userRepository::delete);
    }
}
