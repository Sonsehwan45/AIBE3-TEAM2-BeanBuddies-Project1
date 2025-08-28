package com.back.domain.controller;

import com.back.domain.dto.AnswerForm;
import com.back.domain.entity.Answer;
import com.back.domain.entity.Question;
import com.back.domain.service.AnswerService;
import com.back.domain.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {

    private final AnswerService answerService;
    private final QuestionService questionService;

    // 답변 등록 폼 처리
    @PostMapping("/form/{questionId}")
    public String createAnswer(
            @PathVariable Integer questionId,
            @Valid AnswerForm answerForm,
            BindingResult bindingResult,
            Model model
    ) {
        Question question = questionService.findById(questionId);
        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "post/question/detail"; // 에러 발생 시, 상세 페이지로 다시 렌더링
        }
        answerService.write(question, answerForm.getContent());
        return "redirect:/question/detail/" + questionId;
    }

    // 답변 수정 폼 요청
    @GetMapping("/modify/{id}")
    public String showModify(
            @PathVariable("id") Integer id,
            AnswerForm answerForm, // 1. 폼과 연결할 DTO 파라미터
            Model model
    ) {
        // 2. ID로 기존 답변(Entity)을 조회합니다.
        Answer answer = answerService.findById(id);

        // 3. DTO에 기존 답변의 내용을 채워넣습니다.
        answerForm.setContent(answer.getContent());

        // 4. '취소' 링크에서 질문 ID를 사용하기 위해 answer 엔티티도 모델에 담습니다.
        model.addAttribute("answer", answer);

        // 5. 채워진 DTO와 엔티티를 템플릿으로 전달합니다.
        return "post/answer/modify";
    }

    // 답변 수정 처리
    @PostMapping("/modify/{id}")
    public String modify(
            @PathVariable("id") Integer id,
            @Valid AnswerForm answerForm,
            BindingResult bindingResult,
            Model model
    ) {
        Answer answer = answerService.findById(id);
        if (bindingResult.hasErrors()) {
            model.addAttribute("answer", answer);
            return "post/answer/modify";
        }
        answerService.modify(answer, answerForm.getContent());
        return "redirect:/question/detail/" + answer.getQuestion().getId();
    }

    // 답변 삭제 처리
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        Answer answer = answerService.findById(id);
        answerService.delete(answer);
        return "redirect:/question/detail/" + answer.getQuestion().getId();
    }
}