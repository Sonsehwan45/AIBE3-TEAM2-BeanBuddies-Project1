package com.back.domain.entity;

import com.back.global.jpa.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class SiteUser extends BaseEntity {
    private String username;
    private String password;

    public SiteUser(String username, String password) {
        this.username = username;
        this.password = password;
    }
}