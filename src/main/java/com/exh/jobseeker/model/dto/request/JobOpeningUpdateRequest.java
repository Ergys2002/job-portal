package com.exh.jobseeker.model.dto.request;

import com.exh.jobseeker.model.enums.JobOpeningStatus;
import jakarta.validation.constraints.NotNull;

public class JobOpeningUpdateRequest {
    @NotNull(message = "Status is required")
    private JobOpeningStatus status;

    public JobOpeningStatus getStatus() {
        return status;
    }
}
