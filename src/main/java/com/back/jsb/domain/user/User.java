package com.back.jsb.domain.user;

import com.back.jsb.global.jpa.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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

    @Column(nullable = false)
    private String role;

    public User(UserCreateForm form) {
        this.username = form.getUsername();
        this.password = form.getPassword();
        this.nickname = form.getNickname();
        this.role = "USER";
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthProvider provider = AuthProvider.LOCAL; // LOCAL/KAKAO/GOOGLE...

    @Column(name = "provider_user_id")
    private String providerUserId; // 카카오 id(숫자), 구글은 sub 등

    public enum AuthProvider { LOCAL, KAKAO, GOOGLE }

    // 소셜 가입용 팩토리
    public static User fromKakao(String kakaoId, String nickname, String encodedRandomPw) {
        User u = new User();
        u.provider = AuthProvider.KAKAO;
        u.providerUserId = kakaoId;
        u.username = "kakao_" + kakaoId;                        // 충돌 시 뒤에 난수 덧붙이는 로직 추가 가능
        u.nickname = nickname;
        u.password = encodedRandomPw;                           // NULL 금지 → 랜덤 해시
        u.role = "USER";
        return u;
    }

    // 강한 랜덤 문자열 생성(필요 시 재사용)
    public static String newRandomString() {
        byte[] buf = new byte[32];
        new SecureRandom().nextBytes(buf);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(buf);
    }
}