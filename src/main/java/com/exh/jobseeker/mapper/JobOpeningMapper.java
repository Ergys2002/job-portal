package com.exh.jobseeker.mapper;

import com.exh.jobseeker.model.dto.response.JobOpeningResponse;
import com.exh.jobseeker.model.dto.response.SkillResponse;
import com.exh.jobseeker.model.entity.JobOpening;
import com.exh.jobseeker.model.entity.JobOpeningSkill;
import com.exh.jobseeker.model.entity.Skill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface JobOpeningMapper {

    @Mapping(target = "skills", expression = "java(mapSkills(jobOpening.getJobOpeningSkills()))")
    JobOpeningResponse toJobOpeningResponse(JobOpening jobOpening);

    default Set<SkillResponse> mapSkills(Set<JobOpeningSkill> jobOpeningSkills) {
        if (jobOpeningSkills == null) {
            return null;
        }

        return jobOpeningSkills.stream()
                .map(jobOpeningSkill -> {
                    Skill skill = jobOpeningSkill.getSkill();
                    SkillResponse skillResponse = new SkillResponse();
                    skillResponse.setId(skill.getId());
                    skillResponse.setName(skill.getName());
                    return skillResponse;
                })
                .collect(Collectors.toSet());
    }
}
