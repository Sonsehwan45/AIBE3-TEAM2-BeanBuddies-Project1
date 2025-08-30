package com.back.domain.service;

import com.back.domain.entity.Answer;
import com.back.domain.entity.Question;
import com.back.domain.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AnswerService {

    private final AnswerRepository answerRepository;

    public Answer findById(Integer id) {
        return answerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 ID의 답변을 찾을 수 없습니다: " + id));
    }

    @Transactional
    public Answer write(Question question, String content) {
        Answer answer = new Answer(content, question);
        return this.answerRepository.save(answer);
    }

    @Transactional
    public void modify(Answer answer, String content) {
        answer.update(content);
    }

    @Transactional
    public void delete(Answer answer) {
        answerRepository.delete(answer);
    }
}