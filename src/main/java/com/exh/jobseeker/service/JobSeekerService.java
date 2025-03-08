package com.exh.jobseeker.service;

import com.exh.jobseeker.model.dto.request.JobSeekerCreateRequest;
import com.exh.jobseeker.model.dto.request.SkillCreateRequest;
import com.exh.jobseeker.model.entity.JobSeeker;
import com.exh.jobseeker.model.entity.JobSeekerSkill;
import com.exh.jobseeker.model.entity.Skill;
import com.exh.jobseeker.model.entity.User;
import com.exh.jobseeker.model.enums.EducationLevel;
import com.exh.jobseeker.repository.JobSeekerRepository;
import com.exh.jobseeker.repository.JobSeekerSkillRepository;
import com.exh.jobseeker.repository.SkillRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class JobSeekerService {
    private final JobSeekerRepository jobSeekerRepository;
    private final UserService userService;
    private final FileService fileService;
    private final SkillRepository skillRepository;
    private final JobSeekerSkillRepository jobSeekerSkillRepository;

    public JobSeekerService(
            JobSeekerRepository jobSeekerRepository,
            UserService userService,
            FileService fileService,
            SkillRepository skillRepository,
            JobSeekerSkillRepository jobSeekerSkillRepository
    ) {
        this.jobSeekerRepository = jobSeekerRepository;
        this.userService = userService;
        this.fileService = fileService;
        this.skillRepository = skillRepository;
        this.jobSeekerSkillRepository = jobSeekerSkillRepository;
    }

    @Transactional
    public void saveJobSeeker(JobSeekerCreateRequest jobSeekerData, boolean isNewJobSeeker) {
        User user = userService.getCurrentUser();

        if(isNewJobSeeker){
            createJobSeeker(user, jobSeekerData);
        } else {
            updateJobSeeker(user, jobSeekerData);
        }
    }

    private void createJobSeeker(User user, JobSeekerCreateRequest jobSeekerData) {
        JobSeeker jobSeeker = buildJobSeeker(jobSeekerData);
        jobSeeker.setUser(user);

        String cvPath = fileService.saveFile(jobSeekerData.getCv(), user);
        jobSeeker.setCvUrl(cvPath);

        JobSeeker savedJobSeeker = jobSeekerRepository.save(jobSeeker);

        saveSkills(savedJobSeeker, jobSeekerData.getSkills());
    }

    private void updateJobSeeker(User user, JobSeekerCreateRequest jobSeekerData) {
        JobSeeker jobSeeker = jobSeekerRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Job seeker not found"));

        setJobSeekerData(jobSeeker, jobSeekerData);

        if(jobSeekerData.getCv() != null){
            String cvPath = fileService.saveFile(jobSeekerData.getCv(), user);
            jobSeeker.setCvUrl(cvPath);
        }

        JobSeeker savedJobSeeker = jobSeekerRepository.save(jobSeeker);

        saveSkills(savedJobSeeker, jobSeekerData.getSkills());
    }


    private JobSeeker buildJobSeeker(JobSeekerCreateRequest jobSeekerData) {
        JobSeeker jobSeeker = new JobSeeker();
        jobSeeker.setHeadline(jobSeekerData.getHeadline());
        jobSeeker.setSummary(jobSeekerData.getSummary());
        jobSeeker.setExperienceYears(jobSeekerData.getExperienceYears());
        jobSeeker.setEducationLevel(EducationLevel.valueOf(jobSeekerData.getEducationLevel()));
        return jobSeeker;
    }

    private void setJobSeekerData(JobSeeker jobSeeker, JobSeekerCreateRequest jobSeekerData) {
        jobSeeker.setHeadline(jobSeekerData.getHeadline());
        jobSeeker.setSummary(jobSeekerData.getSummary());
        jobSeeker.setExperienceYears(jobSeekerData.getExperienceYears());
        jobSeeker.setEducationLevel(EducationLevel.valueOf(jobSeekerData.getEducationLevel()));
    }

    private void saveSkills(JobSeeker jobSeeker, Set<SkillCreateRequest> skills) {
        skills.forEach(skillCreateRequest -> {
            skillRepository.findSkillByName(skillCreateRequest.getName()).ifPresentOrElse(
                    skill -> jobSeekerSkillRepository.save(new JobSeekerSkill(jobSeeker, skill)),
                    () -> {
                        Skill savedSkill = skillRepository.save(new Skill(skillCreateRequest.getName()));
                        jobSeekerSkillRepository.save(new JobSeekerSkill(jobSeeker, savedSkill));
                    }
            );
        });
    }
}
