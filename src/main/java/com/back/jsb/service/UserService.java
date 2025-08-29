package com.back.jsb.service;

import com.back.jsb.dto.UserCreateForm;
import com.back.jsb.entity.SiteUser;
import com.back.jsb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public SiteUser join(UserCreateForm form) {
        String encodedPassword = bCryptPasswordEncoder.encode(form.password());
        SiteUser siteUser = new SiteUser(form.username(), encodedPassword, form.email());

        SiteUser user = userRepository.save(siteUser);
        return user;
    }
}