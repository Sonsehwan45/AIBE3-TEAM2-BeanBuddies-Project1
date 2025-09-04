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
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Profile;

@Configuration
@RequiredArgsConstructor
@Profile("dev")
public class BaseInitData {

    private final UserService userService;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;


    @Bean
    @Transactional // 모든 DB 작업이 하나의 트랜잭션으로 묶여 안정성이 높아짐
    public ApplicationRunner initializer() {
        return args -> {
            UserCreateForm userForm1 = new UserCreateForm();
            userForm1.setUsername("user1");
            userForm1.setPassword("user1");
            userForm1.setPasswordConfirm("user1");
            userForm1.setNickname("닉네임1");
            userForm1.setEmail("user1@test.com");
            userService.register(userForm1);

            UserCreateForm userForm2 = new UserCreateForm();
            userForm2.setUsername("user2");
            userForm2.setPassword("user2");
            userForm2.setPasswordConfirm("user2");
            userForm2.setNickname("닉네임2");
            userForm2.setEmail("user2@test.com");
            userService.register(userForm2);

            User user1 = userService.findByUsername("user1");
            User user2 = userService.findByUsername("user2");

            List<Question> Questions = new ArrayList<>();
            for (int i = 1; i <= 45; i++) {
                String title = "질문 %d 제목".formatted(i);
                String content = "질문 %d 내용".formatted(i);
                QuestionForm questionForm = new QuestionForm(title, content);
                User author = (i % 2 == 0) ? user2 : user1;

                Question question = questionRepository.save(new Question(questionForm, author));
                Questions.add(question); // 저장된 질문을 리스트에 추가
            }

            Question firstQuestion = Questions.get(0);
            answerRepository.save(new Answer(new AnswerForm("질문 1에 대한 user2의 답변입니다."), user2, firstQuestion));
            answerRepository.save(new Answer(new AnswerForm("질문 1에 대한 user1의 답변입니다."), user1, firstQuestion));

            Question secondQuestion = Questions.get(1);
            answerRepository.save(new Answer(new AnswerForm("질문 2에 대한 user1의 답변입니다."), user1, secondQuestion));
            answerRepository.save(new Answer(new AnswerForm("질문 2에 대한 user2의 답변입니다."), user2, secondQuestion));

        };

    }
}