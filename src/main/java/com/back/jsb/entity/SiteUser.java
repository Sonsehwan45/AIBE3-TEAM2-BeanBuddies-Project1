package com.back.jsb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SiteUser extends BaseEntity{

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;
}