package com.back.jsb.domain.answer;

import static org.assertj.core.api.Assertions.assertThat;

import com.back.jsb.domain.question.Question;
import com.back.jsb.domain.question.QuestionForm;
import com.back.jsb.domain.question.QuestionService;
import com.back.jsb.domain.reply.AnswerReply;
import com.back.jsb.domain.reply.ReplyRegisterForm;
import com.back.jsb.domain.reply.ReplyService;
import com.back.jsb.domain.user.User;
import com.back.jsb.domain.user.UserCreateForm;
import com.back.jsb.domain.user.UserService;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AnswerServiceTest {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReplyService replyService;

    @Autowired
    EntityManager em;

    @BeforeEach
    void setup() {
        userService.register(new UserCreateForm("user0", "1234", "1234", "유저0", "123@123.com", null));
        User user = userService.findByUsername("user0");

        questionService.register(new QuestionForm("제목", "내용"), user);
        questionService.register(new QuestionForm("제목", "내용"), user);
        Question question1 = questionService.findById(1L);
        Question question2 = questionService.findById(2L);

        answerService.register(new AnswerForm("답변 1"), question2, user);
        answerService.register(new AnswerForm("답변 02"), question1, user);
        answerService.register(new AnswerForm("답변 333"), question2, user);
    }


    @Test
    @DisplayName("최신 답변으로 sort 테스트")
    void findRecentAnswers() {
        List<Answer> content = answerService.findRecentAnswers(0, 10).getContent();

        assertThat(content.size()).isEqualTo(3);
        assertThat(content.get(0).getContent()).isEqualTo("답변 333");
        assertThat(content.get(1).getContent()).isEqualTo("답변 02");
        assertThat(content.get(2).getContent()).isEqualTo("답변 1");
    }

    @Test
    @DisplayName("응답 저장 테스트")
    void registerReply() {
        User user = userService.findByUsername("user0");
        replyService.registerReply(answerService.findById(1L), new ReplyRegisterForm("reply"), user);

        em.flush();
        em.clear();

        List<AnswerReply> replies = answerService.findById(1L).getReplies();

        assertThat(replies.size()).isEqualTo(1);
        assertThat(replies.get(0).getContent()).isEqualTo("reply");
    }

    @Test
    @DisplayName("응답 삭제 테스트")
    void deleteReply() {
        User user = userService.findByUsername("user0");
        replyService.registerReply(answerService.findById(1L), new ReplyRegisterForm("reply"), user);

        em.flush();
        em.clear();

        Answer before = answerService.findById(1L);
        replyService.deleteReply(before, 1L, user);

        em.flush();
        em.clear();

        Answer after = answerService.findById(1L);
        assertThat(after.getReplies().size()).isEqualTo(0);
    }
}