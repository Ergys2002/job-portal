package com.exh.jobseeker.controller;

import com.exh.jobseeker.model.dto.request.JobApplicationFilter;
import com.exh.jobseeker.model.dto.request.JobApplicationUpdateRequest;
import com.exh.jobseeker.model.dto.response.jobapplication.JobApplicationResponse;
import com.exh.jobseeker.service.JobApplicationService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/job-applications")
public class JobApplicationController {
    private final JobApplicationService jobApplicationService;

    public JobApplicationController(JobApplicationService jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }

    @PostMapping("/{jobOpeningId}")
    @PreAuthorize("hasAuthority('JOB_SEEKER')")
    public ResponseEntity<Void> createJobApplication(@PathVariable("jobOpeningId") String jobOpeningId) {
        jobApplicationService.createJobApplication(jobOpeningId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('EMPLOYER')")
    public ResponseEntity<Void> updateJobApplication(@PathVariable("id") String id, @RequestBody JobApplicationUpdateRequest jobApplicationUpdateRequest) {
        jobApplicationService.updateJobApplication(id, jobApplicationUpdateRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/search")
    @PreAuthorize("hasAnyAuthority('EMPLOYER', 'JOB_SEEKER')")
    public ResponseEntity<Page<? extends JobApplicationResponse>> getFilteredJobApplications(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "createdAt,desc") String[] sort,
            @RequestBody(required = false) JobApplicationFilter filter
    ) {
        return ResponseEntity.ok(jobApplicationService.getJobApplications(page, size, sort, filter));
    }

}
