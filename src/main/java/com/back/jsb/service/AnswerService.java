package com.back.jsb.service;

import com.back.jsb.dto.AnswerForm;
import com.back.jsb.entity.Answer;
import com.back.jsb.entity.Question;
import com.back.jsb.entity.SiteUser;
import com.back.jsb.repository.AnswerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final QuestionService questionService;
    private final AnswerRepository answerRepository;

    public Answer write(Integer questionId, AnswerForm answerForm, SiteUser siteUser) {
        Question question = questionService.findById(questionId);
        Answer answer = new Answer(answerForm.content(), question, siteUser);

        return answerRepository.save(answer);
    }

    public void delete(Integer id) {
        answerRepository.deleteById(id);
    }

    public Answer findById(Integer id) {
        return answerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 답변 없음"));
    }

    public void update(Answer answer, String content) {
        answer.update(content);
        answerRepository.save(answer);
    }
}