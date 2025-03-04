package com.exh.jobseeker.model.enums;

public enum WorkLocationType {
    ONSITE("Onsite", "Work is performed at the company's physical location"),
    REMOTE("Remote", "Work is performed entirely from a remote location"),
    HYBRID("Hybrid", "Work is performed through a mix of onsite and remote work"),
    FLEXIBLE("Flexible", "Work location is flexible and determined case by case"),
    TRAVEL_REQUIRED("Travel Required", "Position requires travel to different locations"),
    OTHER("Other", "Other work location type");
    private final String displayName;
    private final String description;

    WorkLocationType(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
