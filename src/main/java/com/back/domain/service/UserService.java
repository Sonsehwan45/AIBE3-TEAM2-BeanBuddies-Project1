package com.back.domain.service;

import com.back.domain.entity.SiteUser;
import com.back.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(String username, String email, String password) {
        SiteUser user = new SiteUser(username, passwordEncoder.encode(password), email);
        return this.userRepository.save(user);
    }
}