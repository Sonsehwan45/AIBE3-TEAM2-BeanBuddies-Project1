package com.back.jsb.domain.user;

import com.back.jsb.global.jpa.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="SiteUser")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String role;

    public User(UserCreateForm form) {
        this.username = form.getUsername();
        this.password = form.getPassword();
        this.nickname = form.getNickname();
        this.role = "USER";
    }
}