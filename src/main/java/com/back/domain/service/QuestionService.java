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

    public Question write(String title, String content) {
        Question question = new Question(title, content);
        return questionRepository.save(question);
    }

    public Question findById(int id) {
        return questionRepository.findById(id).orElseThrow(() -> new RuntimeException("Question not found"));
    }

    public List<Question> findAll() {
        return questionRepository.findAll();
    }
}