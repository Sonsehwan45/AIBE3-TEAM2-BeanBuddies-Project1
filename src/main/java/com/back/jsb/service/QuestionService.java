package com.back.jsb.service;

import com.back.DataNotFoundException;
import com.back.jsb.entity.Question;
import com.back.jsb.entity.SiteUser;
import com.back.jsb.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public List<Question> getList() {
        return questionRepository.findAll();
    }

    public Question getQuestion(Integer id) {
        Optional<Question> question = questionRepository.findById(id);
        if (question.isPresent()) {
            return question.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }

    public void create(String title, String content, SiteUser user) {
        Question q = new Question();
        q.setTitle(title);
        q.setContent(content);
        q.setAuthor(user);
        questionRepository.save(q);
    }

    public void modify(Question question, String title, String content) {
        question.setTitle(title);
        question.setContent(content);
        this.questionRepository.save(question);
    }

    public void delete(Question question) {
        this.questionRepository.delete(question);
    }

    public List<Question> search(String kwType, String kw) {
        if(kwType.equals("title")) {
            return questionRepository.findByTitleContaining(kw);
        }else if(kwType.equals("content")) {
            return questionRepository.findByContentContaining(kw);
        }
        return questionRepository.findAll();
    }
}