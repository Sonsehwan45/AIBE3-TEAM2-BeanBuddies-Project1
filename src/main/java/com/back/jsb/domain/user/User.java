package com.back.jsb.domain.user;

import com.back.jsb.global.jpa.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    @Lob
    private byte[] profileImage;

    public User(UserCreateForm form) {
        this.username = form.getUsername();
        this.password = form.getPassword();
        this.nickname = form.getNickname();
        this.role = "USER";
        MultipartFile file = form.getProfileImage();
        if (file != null && !file.isEmpty()) {
            try {
                this.profileImage = file.getBytes();
            } catch (IOException e) {
                e.printStackTrace();
                this.profileImage = null; // 실패 시 null 처리
            }
        }
    }
}