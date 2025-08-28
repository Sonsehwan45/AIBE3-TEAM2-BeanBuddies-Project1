package com.back.jsb.service;

import com.back.jsb.dto.AnswerForm;
import com.back.jsb.entity.Answer;
import com.back.jsb.entity.Question;
import com.back.jsb.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final QuestionService questionService;
    private final AnswerRepository answerRepository;

    public Answer write(Integer questionId, AnswerForm answerForm) {
        Question question = questionService.findById(questionId);
        Answer answer = new Answer(answerForm.content(), question);

        return answerRepository.save(answer);
    }

    public void delete(Integer id) {
        answerRepository.deleteById(id);
    }
}