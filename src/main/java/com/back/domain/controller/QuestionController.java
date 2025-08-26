package com.back.domain.controller;

import com.back.domain.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/question")
@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/list")
    public String list() {
        return "question/question_list";
    }


}