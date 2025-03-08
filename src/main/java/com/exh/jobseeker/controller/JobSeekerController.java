package com.exh.jobseeker.controller;

import com.exh.jobseeker.model.dto.request.JobSeekerCreateRequest;
import com.exh.jobseeker.service.JobSeekerService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/job-seekers")
public class JobSeekerController {
    private final JobSeekerService jobSeekerService;

    public JobSeekerController(JobSeekerService jobSeekerService) {
        this.jobSeekerService = jobSeekerService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('JOB_SEEKER')")
    public ResponseEntity<Void> completeUserProfileAsJobSeeker(@Valid @ModelAttribute JobSeekerCreateRequest jobSeekerData) {
        System.out.println("Received education level: " + jobSeekerData.getEducationLevel());

        jobSeekerService.saveJobSeeker(jobSeekerData, true);
        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('JOB_SEEKER')")
    public ResponseEntity<Void> updateJobSeekerProfile(@Valid @ModelAttribute JobSeekerCreateRequest jobSeekerData) {
        jobSeekerService.saveJobSeeker(jobSeekerData, false);
        return ResponseEntity.ok().build();
    }
}
