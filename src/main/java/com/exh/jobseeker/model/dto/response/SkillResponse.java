package com.exh.jobseeker.model.dto.response;

import java.util.UUID;

public class SkillResponse {
    private UUID id;
    private String name;

    public SkillResponse() {
    }
    public SkillResponse(String name, UUID id) {
        this.name = name;
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
