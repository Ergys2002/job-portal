package com.exh.jobseeker.controller;

import com.exh.jobseeker.model.dto.request.JobOpeningCreateRequest;
import com.exh.jobseeker.model.dto.request.JobOpeningFilter;
import com.exh.jobseeker.model.dto.request.JobOpeningUpdateRequest;
import com.exh.jobseeker.model.dto.response.JobOpeningResponse;
import com.exh.jobseeker.service.JobOpeningService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jobs")
public class JobOpeningController {
    private final JobOpeningService jobOpeningService;

    public JobOpeningController(JobOpeningService jobOpeningService) {
        this.jobOpeningService = jobOpeningService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('EMPLOYER')")
    public ResponseEntity<Void> createJobOpening(@RequestBody @Valid JobOpeningCreateRequest jobOpeningCreateRequest) {
        jobOpeningService.createJobOpening(jobOpeningCreateRequest);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/my-listings")
    @PreAuthorize("hasAuthority('EMPLOYER')")
    public ResponseEntity<Page<JobOpeningResponse>> getMyJobOpenings(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "status", required = false) String status
    ) {
        return ResponseEntity.ok(jobOpeningService.getMyJobOpenings(title, status, PageRequest.of(page, size)));
    }

    @PostMapping("/search")
    @PreAuthorize("hasAuthority('JOB_SEEKER')")
    public ResponseEntity<Page<JobOpeningResponse>> searchJobOpenings(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "createdAt,desc") String[] sort,
            @RequestBody(required = false) JobOpeningFilter filter
    ) {
        return ResponseEntity.ok(jobOpeningService.searchJobOpenings(filter, page, size, sort));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('EMPLOYER')")
    public ResponseEntity<Void> updateJobOpening(@PathVariable("id") String id, @RequestBody JobOpeningUpdateRequest status) {
        jobOpeningService.updateJobOpeningStatus(id, status);
        return ResponseEntity.accepted().build();
    }
}
