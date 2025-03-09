package com.exh.jobseeker.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.Set;

@Entity
@Table(name = "employer")
@DynamicUpdate
@SQLDelete(sql = "UPDATE employer SET deleted = true, deleted_at = now() WHERE id = ?")
@SQLRestriction("deleted = false")
public class Employer extends BaseEntity{

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

    @Column(name = "position")
    private String position;

    @OneToMany(mappedBy = "employer")
    private Set<JobOpening> jobOpenings;

    public Employer() {
    }

    public Employer(User user, Company company, String position) {
        this.user = user;
        this.company = company;
        this.position = position;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Set<JobOpening> getJobOpenings() {
        return jobOpenings;
    }

    public void setJobOpenings(Set<JobOpening> jobOpenings) {
        this.jobOpenings = jobOpenings;
    }
}
