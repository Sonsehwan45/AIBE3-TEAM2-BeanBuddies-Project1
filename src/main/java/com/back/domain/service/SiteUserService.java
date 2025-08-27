package com.back.domain.service;

import com.back.domain.entity.SiteUser;
import com.back.domain.repository.SiteUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SiteUserService {
    @Autowired
    private SiteUserRepository siteUserRepository;

    public void write(String username, String password) {
        SiteUser checkUser = siteUserRepository.findByUsername(username).orElse(null);

        if (checkUser != null) {
            throw new IllegalArgumentException("이미 사용중인 아이디입니다.");
        }

        SiteUser siteUser = new SiteUser();

        siteUser.setUsername(username);
        siteUser.setPassword(password);

        siteUserRepository.save(siteUser);
    }
}