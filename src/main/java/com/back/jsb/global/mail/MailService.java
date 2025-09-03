package com.back.jsb.global.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String formEmail;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendTxtEmail(String to, String subject, String content) {
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setFrom(formEmail);
        smm.setTo(to);           // 받는 사람 이메일
        smm.setSubject(subject); // 제목
        smm.setText(content);    // 내용
        mailSender.send(smm);
    }
}
