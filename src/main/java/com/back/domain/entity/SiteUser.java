package com.back.domain.entity;

import com.back.global.jpa.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Setter;

@Entity
@Setter
public class SiteUser extends BaseEntity {
    @Column(unique = true)
    private String username;
    private String password;
}