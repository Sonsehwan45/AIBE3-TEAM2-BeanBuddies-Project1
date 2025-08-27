package com.back.domain.controller;

import com.back.domain.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class QuestionController {
    @Autowired
    private AnswerService answerService;
}