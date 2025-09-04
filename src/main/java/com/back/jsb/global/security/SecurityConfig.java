package com.back.jsb.global.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter.XFrameOptionsMode;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                //몇몇 페이지는 인증 필요, 나머지 페이지는 모두 허용
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/user/profile",
                                "/question/write",
                                "/question/delete/**",
                                "/question/modify/**",
                                "/answer/write/**",
                                "/answer/delete/**",
                                "/answer/modify/**",
                                "/user/password"
                        ).authenticated()
                        .anyRequest().permitAll()
                )

                //커스텀 로그인 페이지 사용 + 로그인 후 직전 페이지로 돌아가기(false)
                .formLogin(form -> form
                    .loginPage("/user/login")
                    .defaultSuccessUrl("/question/list", false)
                    .permitAll()
                )

                .logout(logout -> logout
                        .logoutUrl("/user/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                )

                //세션 유지 (1시간)
                .rememberMe(r -> r
                        .key("uniqueAndSecret")
                        .tokenValiditySeconds(3600)
                )

                .csrf(csrf -> csrf.disable())

                .headers(header -> header
                        .addHeaderWriter(new XFrameOptionsHeaderWriter(
                                XFrameOptionsMode.SAMEORIGIN
                        )));

        return http.build();
    }
}