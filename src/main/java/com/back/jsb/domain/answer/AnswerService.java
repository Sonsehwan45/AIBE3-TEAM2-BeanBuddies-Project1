package com.back.jsb.domain.answer;

import com.back.jsb.domain.question.Question;
import com.back.jsb.domain.user.User;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    public void register(@Valid AnswerForm form, Question question, User user) {
        //form과 User로 Answer 객체 생성
        Answer answer = question.addAnswer(form, user);
        answerRepository.save(answer);
    }

    public Answer findById(Long id) {
        return answerRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        answerRepository.deleteById(id);
    }

    public void modify(Answer answer, AnswerForm form) {
        //수정폼으로 Answer 객체 수정
        answer.modify(form);
        answerRepository.save(answer);
    }

    // 최근 답변 기준 findAll
    public Page<Answer> findRecentAnswers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Direction.DESC, "createdDate");
        return answerRepository.findAll(pageable);
    }
}