package com.back.domain.service;

import com.back.domain.entity.SiteUser;
import com.back.domain.repository.SiteUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SiteUserService {
    private final SiteUserRepository siteUserRepository;
    private final PasswordEncoder passwordEncoder;

    public void write(String username, String password, String passwordCheck, String email) {
        SiteUser siteUser = new SiteUser();

        siteUser.setUsername(username);
        siteUser.setPassword(passwordEncoder.encode(password));
        siteUser.setEmail(email);

        siteUserRepository.save(siteUser);
    }


    public SiteUser findByUsername(String username) {
        return siteUserRepository.findByUsername(username).orElse(null);
    }
}