package com.exh.jobseeker.mapper;

import com.exh.jobseeker.model.dto.response.jobapplication.EmployerJobApplicationResponse;
import com.exh.jobseeker.model.dto.response.jobapplication.JobApplicationResponse;
import com.exh.jobseeker.model.dto.response.jobapplication.JobSeekerApplicationResponse;
import com.exh.jobseeker.model.entity.JobApplication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JobApplicationMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "createdAt", target = "appliedAt")
    @Mapping(source = "updatedAt", target = "lastUpdatedAt")
    @Mapping(source = "jobOpening.id", target = "jobOpeningId")
    @Mapping(source = "jobOpening.title", target = "jobOpeningTitle")
    @Mapping(source = "jobOpening.description", target = "jobDescription")
    @Mapping(source = "jobOpening.minSalary", target = "minSalary")
    @Mapping(source = "jobOpening.maxSalary", target = "maxSalary")
    @Mapping(source = "jobOpening.workLocationType", target = "workLocationType")
    @Mapping(source = "jobOpening.location", target = "location")
    JobApplicationResponse toBaseResponse(JobApplication jobApplication);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "createdAt", target = "appliedAt")
    @Mapping(source = "updatedAt", target = "lastUpdatedAt")
    @Mapping(source = "jobOpening.id", target = "jobOpeningId")
    @Mapping(source = "jobOpening.title", target = "jobOpeningTitle")
    @Mapping(source = "jobOpening.description", target = "jobDescription")
    @Mapping(source = "jobOpening.minSalary", target = "minSalary")
    @Mapping(source = "jobOpening.maxSalary", target = "maxSalary")
    @Mapping(source = "jobOpening.workLocationType", target = "workLocationType")
    @Mapping(source = "jobOpening.location", target = "location")
    @Mapping(source = "jobSeeker.id", target = "jobSeekerId")
    @Mapping(source = "jobSeeker.cvUrl", target = "cvUrl")
    @Mapping(source = "jobSeeker.experienceYears", target = "experienceYears")
    @Mapping(source = "jobSeeker.educationLevel", target = "educationLevel")
    @Mapping(source = "jobSeeker.headline", target = "headline")
    @Mapping(source = "jobSeeker.summary", target = "summary")
    @Mapping(source = "jobSeeker.user.email", target = "email")
    @Mapping(source = "jobSeeker.user.userInfo.firstName", target = "firstName")
    @Mapping(source = "jobSeeker.user.userInfo.lastName", target = "lastName")
    @Mapping(source = "jobSeeker.user.userInfo.phoneNumber", target = "phoneNumber")
    @Mapping(source = "jobSeeker.user.userInfo.gender", target = "gender")
    EmployerJobApplicationResponse toEmployerResponse(JobApplication jobApplication);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "createdAt", target = "appliedAt")
    @Mapping(source = "updatedAt", target = "lastUpdatedAt")
    @Mapping(source = "jobOpening.id", target = "jobOpeningId")
    @Mapping(source = "jobOpening.title", target = "jobOpeningTitle")
    @Mapping(source = "jobOpening.description", target = "jobDescription")
    @Mapping(source = "jobOpening.minSalary", target = "minSalary")
    @Mapping(source = "jobOpening.maxSalary", target = "maxSalary")
    @Mapping(source = "jobOpening.workLocationType", target = "workLocationType")
    @Mapping(source = "jobOpening.location", target = "location")
    @Mapping(source = "jobOpening.employer.id", target = "employerId")
    @Mapping(source = "jobOpening.employer.position", target = "employerPosition")
    @Mapping(source = "jobOpening.employer.company.id", target = "companyId")
    @Mapping(source = "jobOpening.employer.company.name", target = "companyName")
    @Mapping(source = "jobOpening.employer.company.website", target = "companyWebsite")
    @Mapping(source = "jobOpening.employer.company.location", target = "companyLocation")
    @Mapping(source = "jobOpening.employer.company.industry", target = "companyIndustry")
    @Mapping(source = "jobOpening.employer.company.numberOfEmployees", target = "companyNumberOfEmployees")
    @Mapping(source = "jobOpening.employer.company.foundationYear", target = "companyFoundationYear")
    @Mapping(source = "jobOpening.employer.company.description", target = "companyDescription")
    @Mapping(source = "jobOpening.employer.user.email", target = "employerEmail")
    @Mapping(source = "jobOpening.employer.user.userInfo.firstName", target = "employerFirstName")
    @Mapping(source = "jobOpening.employer.user.userInfo.lastName", target = "employerLastName")
    @Mapping(source = "jobOpening.employer.user.userInfo.phoneNumber", target = "employerPhoneNumber")
    JobSeekerApplicationResponse toJobSeekerResponse(JobApplication jobApplication);
}