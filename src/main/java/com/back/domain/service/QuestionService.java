package com.back.domain.service;

import com.back.domain.entity.Question;
import com.back.domain.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    public Question save(Integer id, String title, String content) {
        Question question;

        if (id == null) { // 등록
            question = new Question(); // 등록 시에만 생성일 세팅
        } else { // 수정
            question = questionRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("해당 질문이 존재하지 않습니다."));
        }

        question.setTitle(title);
        question.setContent(content);

        return questionRepository.save(question);
    }

    public Question findById(int id) {
        return questionRepository.findById(id).orElseThrow(() -> new RuntimeException("Question not found"));
    }

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public void delete(Question question) {
        questionRepository.delete(question);
    }

    public List<Question> findByKeyword(String searchType, String keyword) {
        if (searchType == null) {
            return this.findAll();
        }

        return switch (searchType) {
            case "title" -> questionRepository.findByTitleContaining(keyword);
            case "content" -> questionRepository.findByContentContaining(keyword);
            default -> this.findAll();
        };
    }
}