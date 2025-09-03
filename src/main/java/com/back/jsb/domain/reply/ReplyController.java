package com.back.jsb.domain.reply;

import com.back.jsb.domain.answer.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ReplyController {

    private final AnswerService answerService;

}
