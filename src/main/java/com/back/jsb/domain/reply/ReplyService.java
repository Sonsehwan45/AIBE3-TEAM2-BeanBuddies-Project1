package com.back.jsb.domain.reply;

import com.back.jsb.domain.answer.Answer;
import com.back.jsb.domain.answer.AnswerRepository;
import com.back.jsb.domain.answer.AnswerService;
import com.back.jsb.domain.question.QuestionRepository;
import com.back.jsb.domain.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReplyService {

    private final ReplyRepository replyRepository;

    public void registerReply(Answer answer, ReplyRegisterForm replyForm, String username) {
        answer.addReply(replyForm.content(), username);
    }

    public void deleteReply(Answer answer, Long replyId) {
        answer.removeReply(replyId);
    }
}
