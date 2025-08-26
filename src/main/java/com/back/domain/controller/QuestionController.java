package com.back.domain.controller;

import com.back.domain.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
}