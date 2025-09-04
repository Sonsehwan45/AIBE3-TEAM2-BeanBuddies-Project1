package com.back.jsb.domain.answer;

import static org.assertj.core.api.Assertions.*;

import com.back.jsb.domain.question.Question;
import com.back.jsb.domain.question.QuestionForm;
import com.back.jsb.domain.question.QuestionService;
import com.back.jsb.domain.user.User;
import com.back.jsb.domain.user.UserCreateForm;
import com.back.jsb.domain.user.UserService;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class AnswerServiceTest {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private UserService userService;

//    @Test
//    @DisplayName("최신 답변으로 sort 테스트")
//    void findRecentAnswers() {
//        userService.register(new UserCreateForm("user0", "1234", "1234", "유저0"));
//        User user = userService.findByUsername("user0");
//
//        questionService.register(new QuestionForm("제목", "내용"), user);
//        questionService.register(new QuestionForm("제목", "내용"), user);
//        Question question1 = questionService.findById(1L);
//        Question question2 = questionService.findById(2L);
//
//        answerService.register(new AnswerForm("답변 1"), question2, user);
//        answerService.register(new AnswerForm("답변 02"), question1, user);
//        answerService.register(new AnswerForm("답변 333"), question2, user);
//
//        List<Answer> content = answerService.findRecentAnswers(0, 10).getContent();
//
//        assertThat(content.size()).isEqualTo(3);
//        assertThat(content.get(0).getContent()).isEqualTo("답변 333");
//        assertThat(content.get(1).getContent()).isEqualTo("답변 02");
//        assertThat(content.get(2).getContent()).isEqualTo("답변 1");
//    }
}