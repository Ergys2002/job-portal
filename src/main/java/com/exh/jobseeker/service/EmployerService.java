package com.exh.jobseeker.service;

import com.exh.jobseeker.exception.EmployerAlreadyExistsForUserException;
import com.exh.jobseeker.model.dto.request.CompanyCreateRequest;
import com.exh.jobseeker.model.dto.request.EmployerCreateRequest;
import com.exh.jobseeker.model.entity.Company;
import com.exh.jobseeker.model.entity.Employer;
import com.exh.jobseeker.model.entity.User;
import com.exh.jobseeker.repository.CompanyRepository;
import com.exh.jobseeker.repository.EmployerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployerService {
    private final EmployerRepository employerRepository;
    private final UserService userService;
    private final CompanyRepository companyRepository;

    public EmployerService(EmployerRepository employerRepository, UserService userService, CompanyRepository companyRepository) {
        this.employerRepository = employerRepository;
        this.userService = userService;
        this.companyRepository = companyRepository;
    }

    @Transactional
    public void saveEmployer(EmployerCreateRequest employerData, boolean isNewEmployer) {
        User user = userService.getCurrentUser();

        if(isNewEmployer){
            createEmployer(user, employerData);
        } else {
            updateEmployer(user, employerData);
        }
    }

    private void createEmployer(User user, EmployerCreateRequest employerData) {
        employerRepository.findByUser(user)
                .ifPresent(employer -> {
                    throw new EmployerAlreadyExistsForUserException(user);
                });

        Employer employer = buildEmployer(employerData);
        employer.setUser(user);

        Company company = buildCompany(employerData.getCompany());
        Company savedCompany = companyRepository.save(company);

        employer.setCompany(savedCompany);

        employerRepository.save(employer);
    }

    private void updateEmployer(User user, EmployerCreateRequest employerData) {
        Employer employer = employerRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Employer not found"));

        employer.setPosition(employerData.getPosition());

        Company company = employer.getCompany();
        updateCompanyData(company, employerData.getCompany());

        companyRepository.save(company);
        employerRepository.save(employer);
    }

    private void updateCompanyData(Company company, CompanyCreateRequest companyRequest) {
        setCompanyData(company, companyRequest);
    }

    private Employer buildEmployer(EmployerCreateRequest request) {
        Employer employer = new Employer();
        employer.setPosition(request.getPosition());
        return employer;
    }

    private Company buildCompany(CompanyCreateRequest companyRequest) {
        Company company = new Company();
        setCompanyData(company, companyRequest);
        return company;
    }

    private void setCompanyData(Company company, CompanyCreateRequest companyRequest) {
        company.setName(companyRequest.getName());
        company.setDescription(companyRequest.getDescription());
        company.setWebsite(companyRequest.getWebsite());
        company.setNumberOfEmployees(companyRequest.getNumberOfEmployees());
        company.setLocation(companyRequest.getLocation());
        company.setFoundationYear(companyRequest.getFoundationYear());
        company.setIndustry(companyRequest.getIndustry());
    }
}

