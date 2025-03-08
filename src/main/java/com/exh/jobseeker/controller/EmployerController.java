package com.exh.jobseeker.controller;

import com.exh.jobseeker.model.dto.request.EmployerCreateRequest;
import com.exh.jobseeker.service.EmployerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employers")
public class EmployerController {
    private final EmployerService employerService;

    public EmployerController(EmployerService employerService) {
        this.employerService = employerService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('EMPLOYER')")
    public ResponseEntity<Void> completeUserProfileAsEmployer(@RequestBody @Valid EmployerCreateRequest employerData) {
        employerService.saveEmployer(employerData, true);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    @PreAuthorize("hasAuthority('EMPLOYER')")
    public ResponseEntity<Void> updateEmployerProfile(@RequestBody @Valid EmployerCreateRequest employerData) {
        employerService.saveEmployer(employerData, false);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
