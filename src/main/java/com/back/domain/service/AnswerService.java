package com.back.domain.service;

import com.back.domain.entity.Answer;
import com.back.domain.entity.Question;
import com.back.domain.repository.AnswerRepository;
import com.back.domain.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {
    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public Answer save(Integer id, String content, int questionId) {
        Answer answer;

        if (id == null) {
            answer = new Answer();
        } else {
            answer = answerRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("해당 답변이 존재하지 않습니다."));
        }

        if (answer.getQuestion() == null) {
            Question question = questionRepository.findById(questionId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 질문이 존재하지 않습니다."));
            answer.setQuestion(question);
        }

        answer.setContent(content);

        return answerRepository.save(answer);
    }
}