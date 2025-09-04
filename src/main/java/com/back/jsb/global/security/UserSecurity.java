package com.back.jsb.global.security;

import com.back.jsb.domain.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 스프링 시큐리티가 로그인 시 사용자 정보과 권한을 확인하려면 UserDetails라는 인터페이스가 필요
 * UserSecurity는 User 엔티티를 스프링시큐리티가 이해할 수 있는 UserDetails로 변환하는 역할
 * 보통은 PrincipalDetails로 씀?
 */
public class UserSecurity implements UserDetails, OAuth2User {
    private final User user;
    private Map<String, Object> attributes;

    public UserSecurity(User user) {
        this.user = user;
    }

    public UserSecurity(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    public String getNickname() {
        return user.getNickname();
    }

    public User getUser() {
        return this.user;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return String.valueOf(user.getProviderUserId());
    }
}
