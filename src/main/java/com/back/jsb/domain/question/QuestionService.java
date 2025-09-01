package com.back.jsb.domain.question;

import com.back.jsb.domain.answer.Answer;
import com.back.jsb.domain.user.User;
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

        //spec의 람다 함수에 들어가는 변수는 final이어야 함. types를 그대로 못 써서 finalTypes 새로 선언
        List<String> finalTypes =
                (types == null || types.isEmpty() || types.contains("all"))
                        ? List.of("title", "content", "answer", "author")
                        : types;

        //specification으로 types에 따라 keyword OR 검색
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
}