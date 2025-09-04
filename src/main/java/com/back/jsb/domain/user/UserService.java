package com.back.jsb.domain.user;

import com.back.jsb.global.mail.MailService;
import com.back.jsb.global.security.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;


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

    public boolean matchPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public void changePassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    public void resetPassword(User user) {
        String newPassword = PasswordUtil.generateRandomPassword(8);
        this.changePassword(user, newPassword);
        this.sendPasswordEmail(user, newPassword);
    }

    public void sendPasswordEmail(User user, String password) {
        String subject = "초기화된 비밀번호 [%s]".formatted(password);
        String content = "☺️ 안녕하세요! BeansBuddies 입니다!\n🔑 초기화된 비밀번호는 [%s] 입니다!\n✅ 로그인 후 반드시 비밀번호를 변경해주세요!".formatted(password);
        mailService.sendTxtEmail(user.getEmail(), subject, content);
    }
}