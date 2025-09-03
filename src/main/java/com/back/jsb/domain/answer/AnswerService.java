package com.back.jsb.domain.answer;

import com.back.jsb.domain.question.Question;
import com.back.jsb.domain.reply.AnswerReply;
import com.back.jsb.domain.reply.ReplyRegisterForm;
import com.back.jsb.domain.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    public void register(@Valid AnswerForm form, Question question, User user) {
        //form과 User로 Answer 객체 생성
        Answer answer = question.addAnswer(form, user);
        answerRepository.save(answer);
    }

    public Answer findById(Long id) {
        return answerRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        answerRepository.deleteById(id);
    }

    public void modify(Answer answer, AnswerForm form) {
        //수정폼으로 Answer 객체 수정
        answer.modify(form);
        answerRepository.save(answer);
    }

    @Transactional
    public void registerReply(Long answerId, ReplyRegisterForm replyForm, String username) {
        Answer answer = findById(answerId);
        answer.addReply(replyForm.content(), username);
    }

    @Transactional
    public void deleteReply(Long answerId, AnswerReply reply) {
        Answer answer = findById(answerId);
        answer.removeReply(reply);
    }
}