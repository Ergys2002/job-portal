package com.exh.jobseeker.service;

import com.exh.jobseeker.exception.InvalidPathVariableException;
import com.exh.jobseeker.exception.InvalidUserRole;
import com.exh.jobseeker.exception.UserNotFoundException;
import com.exh.jobseeker.model.dto.request.CompanyCreateRequest;
import com.exh.jobseeker.model.entity.Company;
import com.exh.jobseeker.model.entity.Employer;
import com.exh.jobseeker.model.entity.User;
import com.exh.jobseeker.model.enums.Role;
import com.exh.jobseeker.repository.CompanyRepository;
import com.exh.jobseeker.repository.EmployerRepository;
import com.exh.jobseeker.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    private final EmployerRepository employerRepository;
    private final UserService userService;

    public CompanyService(CompanyRepository companyRepository,EmployerRepository employerRepository , UserService userService) {
        this.companyRepository = companyRepository;
        this.employerRepository = employerRepository;
        this.userService = userService;
    }

    public void createCompany(CompanyCreateRequest request) {
        User user = userService.getCurrentUser();

        if(!Role.EMPLOYER.equals(user.getRole())){
            throw new InvalidUserRole(user.getRole().name(), Role.EMPLOYER.name());
        }

        Employer employer = employerRepository.findByUser(user)
                .orElseThrow(UserNotFoundException::new);

        Company company = buildCompany(request);

        Company savedCompany = companyRepository.save(company);

        employer.setCompany(savedCompany);

        employerRepository.save(employer);
    }

    private Company buildCompany(CompanyCreateRequest request) {
        Company company = new Company();
        company.setName(request.getName());
        company.setDescription(request.getDescription());
        company.setWebsite(request.getWebsite());
        company.setNumberOfEmployees(request.getNumberOfEmployees());
        company.setLocation(request.getLocation());
        company.setFoundationYear(request.getFoundationYear());
        company.setIndustry(request.getIndustry());
        return company;
    }

    private UUID toUUID(String id) {
        try {
            return UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new InvalidPathVariableException("Invalid UUID format");
        }
    }
}
