package com.back.domain.service;

import com.back.domain.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AnswerService {
    private final AnswerRepository answerRepository;


}