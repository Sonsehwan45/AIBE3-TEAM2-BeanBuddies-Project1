package com.back.jsb.global.initData;

import com.back.jsb.domain.answer.Answer;
import com.back.jsb.domain.answer.AnswerForm;
import com.back.jsb.domain.answer.AnswerRepository;
import com.back.jsb.domain.question.Question;
import com.back.jsb.domain.question.QuestionForm;
import com.back.jsb.domain.question.QuestionRepository;
import com.back.jsb.domain.user.User;
import com.back.jsb.domain.user.UserCreateForm;
import com.back.jsb.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BaseInitData {

    private final UserService userService; // User는 서비스 통해 저장
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Bean
    public ApplicationRunner initializer() {
        return args -> {

            //사용자1 생성
            UserCreateForm userForm1 = new UserCreateForm();
            userForm1.setUsername("user1");
            userForm1.setPassword("user1");
            userForm1.setPasswordConfirm("user1");
            userForm1.setNickname("닉네임1");
            userService.register(userForm1);

            //사용자2 생성
            UserCreateForm userForm2 = new UserCreateForm();
            userForm2.setUsername("user2");
            userForm2.setPassword("user2");
            userForm2.setPasswordConfirm("user2");
            userForm2.setNickname("닉네임2");
            userService.register(userForm2);

            //User 객체 가져오기
            User user1 = userService.existsByUsername("user1") ? userService.findByUsername("user1") : null;
            User user2 = userService.existsByUsername("user2") ? userService.findByUsername("user2") : null;

            //질문 생성
            Question q1 = questionRepository.save(new Question(new QuestionForm("질문 1 제목", "질문 1 내용"), user1));
            Question q2 = questionRepository.save(new Question(new QuestionForm("질문 2 제목", "질문 2 내용"), user2));

            //답변 생성
            answerRepository.save(new Answer(new AnswerForm("질문 1의 답변 1"), user2, q1));
            answerRepository.save(new Answer(new AnswerForm("질문 1의 답변 2"), user1, q1));
            answerRepository.save(new Answer(new AnswerForm("질문 2의 답변 1"), user1, q2));
            answerRepository.save(new Answer(new AnswerForm("질문 2의 답변 2"), user2, q2));
            answerRepository.save(new Answer(new AnswerForm("질문 2의 답변 3"), user2, q2));
        };
    }
}
