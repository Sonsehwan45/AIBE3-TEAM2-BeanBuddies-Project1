package com.back.jsb.domain.question;

import com.back.jsb.domain.answer.Answer;
import com.back.jsb.domain.user.User;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    public void register(QuestionForm form, User user) {
        //form과 user로 Question 객체 생성
        Question question = new Question(form, user);
        questionRepository.save(question);
    }

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public Question findById(Long id) {
        return questionRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        questionRepository.deleteById(id);
    }

    public void modify(Question question, QuestionForm form) {
        //수정 폼으로 Question 객체 수정
        question.modify(form);
        questionRepository.save(question);
    }

    public List<Question> search(List<String> types, String keyword) {

        List<String> finalTypes =
                (types == null || types.isEmpty() || types.contains("all"))
                        ? List.of("title", "content", "answer", "author")
                        : types;

        Specification<Question> spec = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(finalTypes.contains("title")) {
                predicates.add(builder.like(root.get("title"), "%"+keyword+"%"));
            }
            if(finalTypes.contains("content")) {
                predicates.add(builder.like(root.get("content"), "%"+keyword+"%"));
            }
            if(finalTypes.contains("author")) {
                predicates.add(builder.like(root.get("author").get("username"), "%"+keyword+"%"));
            }
            if(finalTypes.contains("answer")) {
                Join<Question, Answer> answers = root.join("answers", JoinType.LEFT);
                predicates.add(builder.like(answers.get("content"), "%" + keyword + "%"));
            }
            query.distinct(true);
            return builder.or(predicates.toArray(new Predicate[0]));
        };

        return questionRepository.findAll(spec);
    }

    private Specification<Question> createSpecification(List<String> types, String keyword) {
        return (root, query, criteriaBuilder) -> {
            if (keyword == null || keyword.trim().isEmpty() || types == null || types.isEmpty()) {
                return criteriaBuilder.conjunction(); // 조건이 없으면 항상 true
            }

            List<Predicate> predicates = new ArrayList<>();
            for (String type : types) {
                switch (type) {
                    case "title":
                        predicates.add(criteriaBuilder.like(root.get("title"), "%" + keyword + "%"));
                        break;
                    case "content":
                        predicates.add(criteriaBuilder.like(root.get("content"), "%" + keyword + "%"));
                        break;
                }
            }

            // 모든 OR 조건을 묶어서 반환
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }

    public Page<Question> getList(int page, List<String> types, String keyword) {
        Pageable pageable = PageRequest.of(page, 10);
        Specification<Question> spec = createSpecification(types, keyword);
        return this.questionRepository.findAll(spec, pageable);
    }
}