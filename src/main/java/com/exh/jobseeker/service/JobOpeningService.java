package com.exh.jobseeker.service;

import com.exh.jobseeker.exception.EmployerProfileNotCompletedException;
import com.exh.jobseeker.exception.JobOpeningDoesNotExist;
import com.exh.jobseeker.mapper.JobOpeningMapper;
import com.exh.jobseeker.model.dto.request.JobOpeningCreateRequest;
import com.exh.jobseeker.model.dto.request.JobOpeningFilter;
import com.exh.jobseeker.model.dto.request.JobOpeningUpdateRequest;
import com.exh.jobseeker.model.dto.request.SkillCreateRequest;
import com.exh.jobseeker.model.dto.response.JobOpeningResponse;
import com.exh.jobseeker.model.entity.Employer;
import com.exh.jobseeker.model.entity.JobOpening;
import com.exh.jobseeker.model.entity.JobOpeningSkill;
import com.exh.jobseeker.model.entity.JobSeeker;
import com.exh.jobseeker.model.entity.Skill;
import com.exh.jobseeker.model.entity.User;
import com.exh.jobseeker.model.enums.JobOpeningStatus;
import com.exh.jobseeker.model.enums.WorkLocationType;
import com.exh.jobseeker.repository.EmployerRepository;
import com.exh.jobseeker.repository.JobOpeningRepository;
import com.exh.jobseeker.repository.JobOpeningSkillRepository;
import com.exh.jobseeker.repository.JobSeekerRepository;
import com.exh.jobseeker.repository.SkillRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class JobOpeningService {
    private final JobOpeningRepository jobOpeningRepository;
    private final EmployerRepository employerRepository;
    private final UserService userService;
    private final SkillRepository skillRepository;
    private final JobOpeningSkillRepository jobOpeningSkillRepository;
    private final JobOpeningMapper jobOpeningMapper;
    private final JobSeekerRepository jobSeekerRepository;

    public JobOpeningService(
            JobOpeningRepository jobOpeningRepository,
            EmployerRepository employerRepository,
            UserService userService,
            SkillRepository skillRepository,
            JobOpeningSkillRepository jobOpeningSkillRepository,
            JobOpeningMapper jobOpeningMapper,
            JobSeekerRepository jobSeekerRepository
    ) {
        this.jobOpeningRepository = jobOpeningRepository;
        this.employerRepository = employerRepository;
        this.userService = userService;
        this.skillRepository = skillRepository;
        this.jobOpeningSkillRepository = jobOpeningSkillRepository;
        this.jobOpeningMapper = jobOpeningMapper;
        this.jobSeekerRepository = jobSeekerRepository;
    }

    @Transactional
    public void createJobOpening(JobOpeningCreateRequest jobOpeningCreateRequest) {
        User currentUser = userService.getCurrentUser();
        Employer employer = employerRepository.findByUser(currentUser).orElseThrow(() -> new EmployerProfileNotCompletedException(currentUser));

        JobOpening jobOpening = buildJobOpening(jobOpeningCreateRequest);

        jobOpening.setEmployer(employer);

        JobOpening savedJobOpening = jobOpeningRepository.save(jobOpening);

        saveSkills(savedJobOpening, jobOpeningCreateRequest.getSkills());
    }


    private JobOpening buildJobOpening(JobOpeningCreateRequest jobOpeningCreateRequest) {
        JobOpening jobOpening = new JobOpening();
        jobOpening.setTitle(jobOpeningCreateRequest.getTitle());
        jobOpening.setDescription(jobOpeningCreateRequest.getDescription());
        jobOpening.setApplicationDeadline(jobOpeningCreateRequest.getApplicationDeadline());
        jobOpening.setStatus(JobOpeningStatus.ACTIVE);
        jobOpening.setMinSalary(jobOpeningCreateRequest.getMinSalary());
        jobOpening.setMaxSalary(jobOpeningCreateRequest.getMaxSalary());
        jobOpening.setWorkLocationType(jobOpeningCreateRequest.getWorkLocationType());

        return jobOpening;
    }

    private void saveSkills(JobOpening jobOpening, Set<SkillCreateRequest> skills) {
        skills.forEach(skillCreateRequest -> {
            skillRepository.findSkillByName(skillCreateRequest.getName()).ifPresentOrElse(
                    skill -> jobOpeningSkillRepository.save(new JobOpeningSkill(jobOpening, skill)),
                    () -> {
                        Skill savedSkill = skillRepository.save(new Skill(skillCreateRequest.getName()));
                        jobOpeningSkillRepository.save(new JobOpeningSkill(jobOpening, savedSkill));
                    }
            );
        });
    }

    @Transactional(readOnly = true)
    public Page<JobOpeningResponse> getMyJobOpenings(String title, String status, PageRequest pageRequest) {
        User currentUser = userService.getCurrentUser();
        Employer employer = employerRepository.findByUser(currentUser).orElseThrow(() -> new EmployerProfileNotCompletedException(currentUser));

        Page<JobOpening> jobOpenings = jobOpeningRepository.findJobOpeningsFiltered(employer, JobOpeningStatus.valueOf(status), title, pageRequest);

        return jobOpenings.map(jobOpeningMapper::toJobOpeningResponse);
    }

    @Transactional
    public void updateJobOpeningStatus(String id, JobOpeningUpdateRequest status) {
        JobOpening jobOpening = jobOpeningRepository.findById(UUID.fromString(id)).orElseThrow(() -> new JobOpeningDoesNotExist(id));

        jobOpening.setStatus(status.getStatus());

        jobOpeningRepository.save(jobOpening);
    }

    @Transactional(readOnly = true)
    public Page<JobOpeningResponse> searchJobOpenings(JobOpeningFilter filter, int page, int size, String[] sort) {
        PageRequest pageRequest = createPageRequest(page, size, createSortOrder(sort));

        boolean hasFilters = filter != null && (
                filter.getTitle() != null ||
                        filter.getPreferredSalary() != null ||
                        filter.getWorkLocationType() != null ||
                        filter.getLocation() != null);

        if(hasFilters){
            WorkLocationType workLocationType = null;
            if (filter.getWorkLocationType() != null) {
                try {
                    workLocationType = WorkLocationType.valueOf(filter.getWorkLocationType().toUpperCase());
                } catch (IllegalArgumentException ignored) {
                }
            }

            return jobOpeningRepository.findJobOpeningsWithFilters(
                    filter.getTitle(),
                    filter.getPreferredSalary(),
                    workLocationType,
                    filter.getLocation(),
                    pageRequest
            ).map(jobOpeningMapper::toJobOpeningResponse);
        }

        try{
            User currentUser = userService.getCurrentUser();

            Optional<JobSeeker> jobSeekerOpt = jobSeekerRepository.findByUser(currentUser);

            if (jobSeekerOpt.isPresent() && hasSkills(jobSeekerOpt.get())) {
                Page<JobOpening> jobOpenings = jobOpeningRepository
                        .findJobOpeningsByJobSeekerSkills(currentUser.getId(), pageRequest);

                if (jobOpenings.getTotalElements() > 0) {
                    return jobOpenings.map(jobOpeningMapper::toJobOpeningResponse);
                }
            }


        } catch (Exception e){
            System.out.println("No skills matched " + e);
        }

        List<JobOpening> randomJobs = jobOpeningRepository
                .findRandomActiveJobOpenings(pageRequest.getPageSize());

        if (randomJobs.isEmpty()) {
            return Page.empty(pageRequest);
        }

        Page<JobOpening> jobOpeningsPage = new PageImpl<>(
                randomJobs,
                pageRequest,
                randomJobs.size());

        return jobOpeningsPage.map(jobOpeningMapper::toJobOpeningResponse);
    }

    private PageRequest createPageRequest(int page, int size, Sort sort) {
        return PageRequest.of(page, size, sort);
    }

    private Sort createSortOrder(String[] sort) {
        if (sort.length > 1) {
            String sortField = sort[0];
            String sortDirection = sort[1];

            return sortDirection.equalsIgnoreCase("asc") ?
                    Sort.by(sortField).ascending() :
                    Sort.by(sortField).descending();
        }

        return Sort.by("createdAt").descending();
    }

    private boolean hasSkills(JobSeeker jobSeeker) {
        return jobSeeker.getSkills() != null && !jobSeeker.getSkills().isEmpty();
    }
}
