package com.back.domain.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // 이 클래스가 스프링의 설정 클래스임을 나타냅니다.
@EnableWebSecurity // 모든 웹 요청이 스프링 시큐리티의 통제를 받도록 합니다.
public class SecurityConfig {

    @Bean // 이 메서드가 반환하는 객체를 스프링의 Bean으로 등록합니다.
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // HTTP 요청에 대한 접근 권한을 설정합니다.
                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        // "/**"는 모든 URL 경로를 의미하며, permitAll()은 모든 사용자에게 접근을 허용합니다.
                        .requestMatchers("/**").permitAll())
                // 로그인 설정을 구성합니다.
                .formLogin((formLogin) -> formLogin
                        // 로그인 페이지의 URL을 "/user/login"으로 지정합니다.
                        .loginPage("/user/login")
                        // 로그인 성공 시 기본적으로 이동할 URL을 지정합니다.
                        .defaultSuccessUrl("/question/list"))
                // 로그아웃 설정을 구성합니다.
                .logout((logout) -> logout
                        // 로그아웃을 처리할 URL을 "/user/logout"으로 지정합니다. (POST 방식)
                        .logoutUrl("/user/logout")
                        // 로그아웃 성공 시 이동할 URL을 지정합니다.
                        .logoutSuccessUrl("/question/list")
                        // 로그아웃 시 사용자 세션을 무효화(삭제)합니다.
                        .invalidateHttpSession(true));

        return http.build(); // 설정된 HttpSecurity 객체를 빌드하여 반환합니다.
    }

    @Bean // 비밀번호 암호화 방식을 스프링 Bean으로 등록합니다.
    PasswordEncoder passwordEncoder() {
        // BCrypt 해시 알고리즘을 사용하는 PasswordEncoder를 생성합니다.
        return new BCryptPasswordEncoder();
    }
}