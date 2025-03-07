package com.exh.jobseeker.service;

import com.exh.jobseeker.exception.NoAuthenticatedUserException;
import com.exh.jobseeker.exception.UserNotFoundException;
import com.exh.jobseeker.model.entity.User;
import com.exh.jobseeker.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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

}
