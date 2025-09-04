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
        question.modify(form);
        questionRepository.save(question);
    }

    private Specification<Question> createSpecification(List<String> types, String keyword) {
        return (root, query, builder) -> {
            // 키워드가 없으면 아무 조건도 적용하지 않습니다 (전체 리스트 조회를 위함).
            if (keyword == null || keyword.trim().isEmpty()) {
                return builder.conjunction();
            }
            List<String> finalTypes =
                    (types == null || types.isEmpty() || types.contains("all"))
                            ? List.of("title", "content", "answer", "author")
                            : types;

            List<Predicate> predicates = new ArrayList<>();

            if (finalTypes.contains("title")) {
                predicates.add(builder.like(root.get("title"), "%" + keyword + "%"));
            }
            if (finalTypes.contains("content")) {
                predicates.add(builder.like(root.get("content"), "%" + keyword + "%"));
            }
            if (finalTypes.contains("author")) {
                predicates.add(builder.like(root.get("author").get("username"), "%" + keyword + "%"));
            }
            if (finalTypes.contains("answer")) {
                // Question과 Answer를 LEFT JOIN으로 연결합니다.
                Join<Question, Answer> answers = root.join("answers", JoinType.LEFT);
                predicates.add(builder.like(answers.get("content"), "%" + keyword + "%"));
            }

            query.distinct(true);

            return builder.or(predicates.toArray(new Predicate[0]));
        };
    }

    public Page<Question> getList(int page, List<String> types, String keyword) {
        Pageable pageable = PageRequest.of(page, 10);
        Specification<Question> spec = createSpecification(types, keyword);
        return this.questionRepository.findAll(spec, pageable);
    }
}