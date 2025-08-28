package com.back.domain.service;


import com.back.domain.entity.Question;
import com.back.domain.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public long count() {
        return questionRepository.count();
    }

    public Question write(String title, String content) {
        Question question = new Question(title, content);

        return questionRepository.save(question);
    }

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public Question findById(Integer id) {
        return questionRepository.findById(id).orElseThrow(() -> new RuntimeException("예외발생"));
    }

    @Transactional
    public void modify(Question question, String title, String content) {
        question.update(title, content);
    }

    public void delete(Integer id) {
        Question question = findById(id);

        questionRepository.delete(question);
    }

    public List<Question> search(String kw) {
        return questionRepository.findAllByKeyword(kw);
    }
}