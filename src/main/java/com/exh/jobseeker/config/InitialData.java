package com.exh.jobseeker.config;


import com.exh.jobseeker.model.entity.RefreshToken;
import com.exh.jobseeker.model.entity.User;
import com.exh.jobseeker.model.entity.UserInfo;
import com.exh.jobseeker.model.enums.Gender;
import com.exh.jobseeker.model.enums.Role;
import com.exh.jobseeker.repository.UserInfoRepository;
import com.exh.jobseeker.repository.UserRepository;
import com.exh.jobseeker.service.RefreshTokenService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class InitialData implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserInfoRepository userInfoRepository;
    private final RefreshTokenService refreshTokenService;

    public InitialData(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            UserInfoRepository userInfoRepository,
            RefreshTokenService refreshTokenService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userInfoRepository = userInfoRepository;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public void run(String... args)  {
        if (userRepository.findByEmail("admin@admin.com") == null) {
            try {
                saveAdmin();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Admin account created successfully");
        } else {
            System.out.println("Admin account already exists");
        }
    }

    @Transactional
    public void saveAdmin() throws ParseException {
        User admin = new User();
        admin.setEmail("admin@admin.com");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setRole(Role.ADMIN);

        User savedAdmin = userRepository.save(admin);

        refreshTokenService.createRefreshToken(savedAdmin.getEmail());

        UserInfo userInfo = new UserInfo();
        userInfo.setFirstName("Admin");
        userInfo.setLastName("Admin");
        userInfo.setPhoneNumber("1234567890");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = dateFormat.parse("2002-01-01");
        userInfo.setBirthDate(birthDate);
        userInfo.setUser(savedAdmin);
        userInfo.setGender(Gender.MALE);

        userInfoRepository.save(userInfo);
    }


}
