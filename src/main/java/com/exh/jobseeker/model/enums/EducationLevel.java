package com.exh.jobseeker.model.enums;

public enum EducationLevel {
    HIGH_SCHOOL("High School"),
    VOCATIONAL_TRAINING("Vocational Training"),
    ASSOCIATE_DEGREE("Associate Degree"),
    BACHELORS_DEGREE("Bachelor's Degree"),
    MASTERS_DEGREE("Master's Degree"),
    DOCTORAL_DEGREE("Doctoral Degree"),
    POST_DOCTORAL("Post-Doctoral"),
    PROFESSIONAL_DEGREE("Professional Degree"),
    CERTIFICATION("Professional Certification"),
    SOME_COLLEGE("Some College"),
    OTHER("Other");

    private final String displayName;

    EducationLevel(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
