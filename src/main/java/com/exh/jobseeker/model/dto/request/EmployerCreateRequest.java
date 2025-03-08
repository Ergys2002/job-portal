package com.exh.jobseeker.model.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EmployerCreateRequest {
    @NotBlank(message = "Position is required")
    private String position;
    @Valid
    @NotNull(message = "Company is required")
    private CompanyCreateRequest company;

    public String getPosition() {
        return position;
    }

    public CompanyCreateRequest getCompany() {
        return company;
    }
}
