package com.exh.jobseeker.controller;

import com.exh.jobseeker.model.dto.request.CompanyCreateRequest;
import com.exh.jobseeker.service.CompanyService;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;


    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('EMPLOYER')")
    public ResponseEntity<Void> createCompany(@RequestBody CompanyCreateRequest request) {
        companyService.createCompany(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
