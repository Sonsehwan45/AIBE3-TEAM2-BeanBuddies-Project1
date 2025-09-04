package com.back.jsb.domain.user;

import com.back.jsb.global.jpa.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.security.SecureRandom;
import java.util.Base64;

@Entity
@Table(
        name="SiteUser",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_provider_uid", columnNames = {"provider", "provider_user_id"})
        }
)
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

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String role;

    @Lob
    private byte[] profileImage;

    public User(UserCreateForm form) {
        this.username = form.getUsername();
        this.password = form.getPassword();
        this.nickname = form.getNickname();
        this.role = "USER";
        this.email = form.getEmail();

        MultipartFile file = form.getProfileImage();
        if (file != null && !file.isEmpty()) {
            try {
                this.profileImage = file.getBytes();
            } catch (IOException e) {
                e.printStackTrace();
                this.profileImage = null;
            }
        }
    }

    public void modify(ProfileForm form) {
        this.nickname=form.nickname();
        if(form.deleteProfileImage()) this.profileImage=null;
        else if(form.profileImage()!=null) {
            MultipartFile file = form.profileImage();
            if (!file.isEmpty()) {
                try {
                    this.profileImage = file.getBytes();
                } catch (IOException e) {
                    e.printStackTrace();
                    this.profileImage = null;
                }
            }
        }
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthProvider provider = AuthProvider.LOCAL;

    @Column(name = "provider_user_id")
    private String providerUserId;

    public enum AuthProvider { LOCAL, KAKAO }

    public static User fromKakao(String kakaoId, String nickname, String encodedRandomPw) {
        User u = new User();
        u.provider = AuthProvider.KAKAO;
        u.providerUserId = kakaoId;
        u.username = "kakao_" + kakaoId;
        u.nickname = nickname;
        u.password = encodedRandomPw;
        u.role = "USER";
        return u;
    }

    public static String newRandomString() {
        byte[] buf = new byte[32];
        new SecureRandom().nextBytes(buf);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(buf);
    }
}