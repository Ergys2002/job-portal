package com.exh.jobseeker.model.dto.request;

import jakarta.validation.constraints.NotBlank;

public class SkillCreateRequest {
    @NotBlank
    private String name;

    public SkillCreateRequest() {
    }

    public SkillCreateRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
