package com.back.global.initData;

import com.back.domain.entity.Question;
import com.back.domain.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class BaseInitData {
    private final QuestionService questionService;

    @Bean
    ApplicationRunner initApplicationRunner() {
        return args -> {
            if (questionService.count() > 0) return;

            Question question1 = questionService.write("제목 1", "내용 1");
            Question question2 = questionService.write("제목 2", "내용 2");
            Question question3 = questionService.write("제목 3", "내용 3");
        };
    }
}