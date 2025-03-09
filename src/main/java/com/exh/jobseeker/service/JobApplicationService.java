package com.exh.jobseeker.service;

import com.exh.jobseeker.exception.EmployerProfileNotCompletedException;
import com.exh.jobseeker.exception.JobSeekerProfileNotCompletedException;
import com.exh.jobseeker.exception.StringToUuidParsingException;
import com.exh.jobseeker.mapper.JobApplicationMapper;
import com.exh.jobseeker.model.dto.request.JobApplicationFilter;
import com.exh.jobseeker.model.dto.request.JobApplicationUpdateRequest;
import com.exh.jobseeker.model.dto.response.jobapplication.EmployerJobApplicationResponse;
import com.exh.jobseeker.model.dto.response.jobapplication.JobApplicationResponse;
import com.exh.jobseeker.model.dto.response.jobapplication.JobSeekerApplicationResponse;
import com.exh.jobseeker.model.entity.Employer;
import com.exh.jobseeker.model.entity.JobApplication;
import com.exh.jobseeker.model.entity.JobApplicationReview;
import com.exh.jobseeker.model.entity.JobOpening;
import com.exh.jobseeker.model.entity.JobSeeker;
import com.exh.jobseeker.model.entity.User;
import com.exh.jobseeker.model.enums.JobApplicationStatus;
import com.exh.jobseeker.model.enums.Role;
import com.exh.jobseeker.repository.EmployerRepository;
import com.exh.jobseeker.repository.JobApplicationRepository;
import com.exh.jobseeker.repository.JobOpeningRepository;
import com.exh.jobseeker.repository.JobSeekerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class JobApplicationService {
    private final JobApplicationRepository jobApplicationRepository;
    private final UserService userService;
    private final JobSeekerRepository jobSeekerRepository;
    private final JobOpeningRepository jobOpeningRepository;
    private final EmployerRepository employerRepository;
    private final JobApplicationMapper jobApplicationMapper;
    private final EmailService emailService;

    public JobApplicationService(
            JobApplicationRepository jobApplicationRepository,
            UserService userService,
            JobSeekerRepository jobSeekerRepository,
            JobOpeningRepository jobOpeningRepository,
            EmployerRepository employerRepository,
            JobApplicationMapper jobApplicationMapper,
            EmailService emailService
    ) {
        this.jobApplicationRepository = jobApplicationRepository;
        this.userService = userService;
        this.jobSeekerRepository = jobSeekerRepository;
        this.jobOpeningRepository = jobOpeningRepository;
        this.employerRepository = employerRepository;
        this.jobApplicationMapper = jobApplicationMapper;
        this.emailService = emailService;
    }

    @Transactional
    public void createJobApplication(String jobOpeningId) {
        User currentUser = userService.getCurrentUser();
        JobSeeker jobSeeker = jobSeekerRepository.findByUser(currentUser)
                .orElseThrow(() -> new JobSeekerProfileNotCompletedException(currentUser));

        UUID jobId = parseToUUID(jobOpeningId);
        JobOpening jobOpening = jobOpeningRepository.findById(jobId)
                .orElseThrow(() -> new IllegalArgumentException("Job opening not found with id: " + jobId));

        validateJobApplicationData(jobSeeker, jobOpening);

        JobApplication jobApplication = new JobApplication(jobOpening, jobSeeker, JobApplicationStatus.APPLIED);

        jobApplicationRepository.save(jobApplication);

        emailService.sendJobApplicationStatusUpdateEmail(jobApplication);
    }

    @Transactional
    public void updateJobApplication(String id, JobApplicationUpdateRequest jobApplicationUpdateRequest) {
        UUID jobApplicationId = parseToUUID(id);
        JobApplication jobApplication = jobApplicationRepository.findById(jobApplicationId)
                .orElseThrow(() -> new IllegalArgumentException("Job application not found with id: " + jobApplicationId));

        if (jobApplicationUpdateRequest.getReview() == null && !JobApplicationStatus.REJECTED.equals(jobApplicationUpdateRequest.getStatus())) {
            jobApplication.setStatus(jobApplicationUpdateRequest.getStatus());
        } else {
            jobApplication.setStatus(JobApplicationStatus.REJECTED);
            JobApplicationReview review = new JobApplicationReview();
            review.setRating(jobApplicationUpdateRequest.getReview().getRating());
            review.setComment(jobApplicationUpdateRequest.getReview().getComment());
            jobApplication.setJobApplicationReview(review);
        }
        jobApplicationRepository.save(jobApplication);
    }

    private void validateJobApplicationData(JobSeeker jobSeeker, JobOpening jobOpening) {
        if (jobApplicationRepository.existsByJobSeekerAndJobOpening(jobSeeker, jobOpening)) {
            throw new IllegalArgumentException("Job application already exists for job seeker: " + jobSeeker.getId() + " and job opening: " + jobOpening.getId());
        }

        if (jobOpening.getApplicationDeadline().before(Date.from(Instant.now()))) {
            throw new IllegalArgumentException("Job application deadline has passed for job opening: " + jobOpening.getId());
        }
    }

    private UUID parseToUUID(String id) {
        if (id == null) {
            return null;
        }
        try {
            return UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new StringToUuidParsingException("Failed to parse string to UUID: " + id);
        }
    }

    @Transactional(readOnly = true)
    public Page<? extends JobApplicationResponse> getJobApplications(int page, int size, String[] sort, JobApplicationFilter filter) {
        User currentUser = userService.getCurrentUser();
        PageRequest pageRequest = createPageRequest(page, size, createSortOrder(sort));

        if(Role.EMPLOYER.equals(currentUser.getRole())) {
            return getJobApplicationsForEmployer(pageRequest, filter, currentUser);
        } else {
            return getJobApplicationsForJobSeeker(pageRequest, filter, currentUser);
        }
    }

    private Page<EmployerJobApplicationResponse> getJobApplicationsForEmployer(PageRequest pageRequest, JobApplicationFilter filter, User currentUser) {
        Employer employer = employerRepository.findByUser(currentUser)
                .orElseThrow(() -> new EmployerProfileNotCompletedException(currentUser));

        UUID employerId = employer.getId();
        Page<JobApplication> jobApplications;

        if (filter != null && (filter.getJobOpeningId() != null ||
                filter.getJobOpeningTitle() != null ||
                filter.getStatus() != null)) {

            jobApplications = jobApplicationRepository.findJobApplicationsForEmployer(
                    employerId,
                    filter.getJobOpeningId(),
                    filter.getJobOpeningTitle(),
                    filter.getStatus(),
                    pageRequest);
        } else {
            jobApplications = jobApplicationRepository.findAllByJobOpeningEmployerId(employerId, pageRequest);
        }

        return jobApplications.map(jobApplicationMapper::toEmployerResponse);
    }

    private Page<JobSeekerApplicationResponse> getJobApplicationsForJobSeeker(PageRequest pageRequest, JobApplicationFilter filter, User currentUser) {
        JobSeeker jobSeeker = jobSeekerRepository.findByUser(currentUser)
                .orElseThrow(() -> new JobSeekerProfileNotCompletedException(currentUser));

        UUID jobSeekerId = jobSeeker.getId();
        Page<JobApplication> jobApplications;

        if (filter != null && (filter.getJobOpeningTitle() != null || filter.getStatus() != null)) {
            jobApplications = jobApplicationRepository.findJobApplicationsForJobSeeker(
                    jobSeekerId,
                    filter.getJobOpeningTitle(),
                    filter.getStatus(),
                    pageRequest);
        } else {
            jobApplications = jobApplicationRepository.findAllByJobSeekerId(jobSeekerId, pageRequest);
        }

        return jobApplications.map(jobApplicationMapper::toJobSeekerResponse);
    }

    private PageRequest createPageRequest(int page, int size, Sort sort) {
        return PageRequest.of(page, size, sort);
    }

    private Sort createSortOrder(String[] sort) {
        if (sort != null && sort.length > 1) {
            String sortField = sort[0];
            String sortDirection = sort[1];

            return sortDirection.equalsIgnoreCase("asc") ?
                    Sort.by(sortField).ascending() :
                    Sort.by(sortField).descending();
        }

        return Sort.by("createdAt").descending();
    }
}
