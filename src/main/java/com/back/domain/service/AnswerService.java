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

    // ID로 답변 조회
    public Answer findById(Integer id) {
        return answerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 ID의 답변을 찾을 수 없습니다: " + id));
    }

    // 답변 생성
    @Transactional
    public Answer write(Question question, String content) {
        Answer answer = new Answer(content, question);
        return this.answerRepository.save(answer);
    }

    // 답변 수정
    @Transactional
    public void modify(Answer answer, String content) {
        answer.update(content);
    }

    // 답변 삭제
    @Transactional
    public void delete(Answer answer) {
        answerRepository.delete(answer);
    }
}