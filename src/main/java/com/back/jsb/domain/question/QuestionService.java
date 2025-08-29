package com.back.jsb.domain.question;

import com.back.jsb.domain.answer.Answer;
import com.back.jsb.domain.user.User;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<Question> search(List<String> types, String keyword) {

        Specification<Question> spec = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(types.contains("title")) {
                predicates.add(builder.like(root.get("title"), "%"+keyword+"%"));
            }
            if(types.contains("content")) {
                predicates.add(builder.like(root.get("content"), "%"+keyword+"%"));
            }
            if(types.contains("author")) {
                predicates.add(builder.like(root.get("author").get("username"), "%"+keyword+"%"));
            }
            if(types.contains("answer")) {
                Join<Question, Answer> answers = root.join("answers", JoinType.LEFT);
                predicates.add(builder.like(answers.get("content"), "%" + keyword + "%"));
            }
            query.distinct(true);
            return builder.or(predicates.toArray(new Predicate[0]));
        };

        return questionRepository.findAll(spec);
    }
}