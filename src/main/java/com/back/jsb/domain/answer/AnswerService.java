package com.back.jsb.domain.answer;

import com.back.jsb.domain.question.Question;
import com.back.jsb.domain.user.User;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    public void register(@Valid AnswerForm form, Question question, User user) {
        Answer answer = question.addAnswer(form, user);
        answerRepository.save(answer);
    }

    public Answer findById(Long id) {
        return answerRepository.findById(id) .orElseThrow(() ->new EntityNotFoundException("Answer not found"));
    }

    public void deleteById(Long id) {
        answerRepository.deleteById(id);
    }

    public void modify(Answer answer, AnswerForm form) {
        answer.modify(form);
        answerRepository.save(answer);
    }
}