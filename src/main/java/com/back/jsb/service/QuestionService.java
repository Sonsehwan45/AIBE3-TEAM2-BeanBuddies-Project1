package com.back.jsb.service;

import com.back.jsb.entity.Question;
import com.back.jsb.entity.SiteUser;
import com.back.jsb.repository.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public Question findById(Integer id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Id가 %d인 질문 존재하지 않음".formatted(id)));
    }

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public Question write(String title, String content, SiteUser siteUser) {
        Question question = new Question(title, content, siteUser);
        return questionRepository.save(question);
    }

    public void deleteById(Integer id) {
        Question foundQuestion = findById(id);
        questionRepository.delete(foundQuestion);
    }

    public void update(Question question, String title, String content) {
        question.update(title, content);
        questionRepository.save(question);
    }
}