package com.back.domain.entity;

import com.back.global.jpa.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Setter;

@Entity
@Setter
public class SiteUser extends BaseEntity {
    private String username;
    private String password;
}