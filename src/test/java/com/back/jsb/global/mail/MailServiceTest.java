package com.back.jsb.global.mail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MailServiceTest {
    @Autowired
    private MailService mailService;

    @Value("${spring.mail.username}")
    private String testEmail;

    @Test
    @DisplayName("텍스트 메일 기능 확인")
    void t1() {
        mailService.sendTxtEmail(testEmail, "테스트", "테스트 내용");
    }

    @Test
    @DisplayName("텍스트에 이모티콘 추가 확인")
    void t2() {
        mailService.sendTxtEmail(testEmail, "✅이모티콘", "☺️🔑✅테스트 내용");
    }
}