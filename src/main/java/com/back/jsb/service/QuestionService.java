package com.back.jsb.service;

import com.back.jsb.entity.Answer;
import com.back.jsb.entity.Question;
import com.back.jsb.entity.SiteUser;
import com.back.jsb.repository.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public Question findById(Integer id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Id가 %d인 질문 존재하지 않음".formatted(id)));
    }

    public List<Question> findAll(String keyword) {
        if (keyword.equals("")) {
            return questionRepository.findAll();
        }
        Specification<Question> specification = search(keyword);
        return questionRepository.findAll(specification);
    }

    private Specification<Question> search(String keyword) {
        return (question, query, builder) -> {
            query.distinct(true);

            Join<Question, SiteUser> questionUser = question.join("author", JoinType.LEFT);
            Join<Question, Answer> questionAnswer = question.join("answers", JoinType.LEFT);
            Join<Answer, SiteUser> answerUser = questionAnswer.join("author", JoinType.LEFT);
            String searchPattern = "%" + keyword + "%";

            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.like(question.get("title"), searchPattern));
            predicates.add(builder.like(question.get("content"), searchPattern));
            predicates.add(builder.like(questionUser.get("username"), searchPattern));
            predicates.add(builder.like(questionAnswer.get("content"), searchPattern));
            predicates.add(builder.like(answerUser.get("username"), searchPattern));

            return builder.or(predicates.toArray(new Predicate[0]));
        };
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