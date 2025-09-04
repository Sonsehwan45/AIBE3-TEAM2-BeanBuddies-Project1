package com.back.jsb.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public void register(UserCreateForm form) {
        //암호화된 비밀번호로 변경
        form.setPassword(passwordEncoder.encode(form.getPassword()));

        //form으로 User 객체 생성
        User user = new User(form);
        userRepository.save(user);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public void modify(User user, ProfileForm form) {
        user.modify(form);
        userRepository.save(user);
    }
}