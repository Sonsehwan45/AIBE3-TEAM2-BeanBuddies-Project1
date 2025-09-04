package com.back.jsb.domain.reply;

import com.back.jsb.domain.answer.Answer;
import com.back.jsb.domain.answer.AnswerService;
import com.back.jsb.global.security.UserSecurity;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/question/detail/{questionId}/{answerId}")
public class ReplyController {

    private final AnswerService answerService;
    private final ReplyService replyService;

    // 현재 URL 형식상 SecurityConfig에 추가하기 어려움
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/reply/write")
    public String write(
            @PathVariable("questionId") Long questionId,
            @PathVariable("answerId") Long answerId,
            @ModelAttribute("form") @Valid ReplyRegisterForm form,
            @AuthenticationPrincipal UserSecurity userSecurity,
            RedirectAttributes redirectAttributes,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "redirect:/question/detail/%d".formatted(questionId);
        }

        try {
            Answer answer = answerService.findById(answerId);
            String username = userSecurity.getUsername();
            replyService.registerReply(answer, form, username);
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("msg", "해당 답글이 존재하지 않습니다.");
            return "redirect:/question/detail/%d".formatted(questionId);
        }

        return "redirect:/question/detail/%d".formatted(questionId);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("reply/delete/{id}")
    public String delete(
            @PathVariable("questionId") Long questionId,
            @PathVariable("answerId") Long answerId,
            @PathVariable("id") Long id,
            @AuthenticationPrincipal UserSecurity userSecurity,
            RedirectAttributes redirectAttributes
    ) {
        try {
            Answer answer = answerService.findById(answerId);
            String username = userSecurity.getUsername();
            replyService.deleteReply(answer, id, username);
        } catch (EntityNotFoundException e) {
            //TODO : 답글이 존재하지 않는지, 답변이 존재하지 않는지 구분
            redirectAttributes.addFlashAttribute("msg", "해당 답글이 존재하지 않습니다.");
            return "redirect:/question/detail/%d".formatted(questionId);
        } catch (SecurityException e) {
            redirectAttributes.addFlashAttribute("msg", "답글 삭제 권한이 없습니다. 본인만 삭제할 수 있습니다.");
            return "redirect:/question/detail/%d".formatted(questionId);
        }

        return "redirect:/question/detail/%d".formatted(questionId);
    }
}

