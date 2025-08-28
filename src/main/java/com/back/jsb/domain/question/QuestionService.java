package com.back.jsb.domain.question;

import com.back.jsb.domain.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    public void register(QuestionForm form, User user) {
        Question question = new Question(form, user);
        questionRepository.save(question);
    }

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public Question findById(Long id) {
        return questionRepository.findById(id) .orElseThrow(() ->new EntityNotFoundException("Question not found"));
    }

    public void deleteById(Long id) {
        questionRepository.deleteById(id);
    }

    public void modify(Question question, QuestionForm form) {
        question.modify(form);
        questionRepository.save(question);
    }
}