package com.exh.jobseeker.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "company")
@DynamicUpdate
@SQLDelete(sql = "UPDATE company SET deleted = true, deleted_at = now() WHERE id = ?")
@SQLRestriction("deleted = false")
public class Company extends BaseEntity{
    @Column(name = "name", nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;
    private String website;
    @Column(name = "number_of_employees")
    private Integer numberOfEmployees;
    @Column(name = "location", nullable = false)
    private String location;
    @Column(name = "foundation_year")
    private Integer foundationYear;
    @Column(name = "industry", nullable = false)
    private String industry;

    public Company() {
    }

    public Company(String name, String description, String website, Integer numberOfEmployees, String location, Integer foundationYear, String industry) {
        this.name = name;
        this.description = description;
        this.website = website;
        this.numberOfEmployees = numberOfEmployees;
        this.location = location;
        this.foundationYear = foundationYear;
        this.industry = industry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Integer getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public void setNumberOfEmployees(Integer numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getFoundationYear() {
        return foundationYear;
    }

    public void setFoundationYear(Integer foundationYear) {
        this.foundationYear = foundationYear;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }
}
