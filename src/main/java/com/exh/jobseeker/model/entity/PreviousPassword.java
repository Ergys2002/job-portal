package com.exh.jobseeker.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@DynamicUpdate
@SQLDelete(sql = "UPDATE previous_passwoed SET deleted = true, deleted_at = now() WHERE id = ?")
@SQLRestriction("deleted = false")
@Table(name = "previous_password")
public class PreviousPassword extends BaseEntity{
    @Column(name = "password", nullable = false)
    private String password;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
