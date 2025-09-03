package com.back.jsb.domain.question;

import com.back.jsb.domain.answer.Answer;
import com.back.jsb.domain.answer.AnswerForm;
import com.back.jsb.domain.user.User;
import com.back.jsb.global.jpa.entity.BaseEntity;
import jakarta.persistence.*;
import java.util.ArrayList;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Question extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    private User author;

    @OneToMany(mappedBy = "question", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Answer> answers = new ArrayList<>();

    public Question(QuestionForm form, User user) {
        this.title = form.getTitle();
        this.content = form.getContent();
        this.author = user;
    }

    public void modify(QuestionForm form) {
        setTitle(form.getTitle());
        setContent(form.getContent());
    }

    public Answer addAnswer(AnswerForm form, User user) {
        Answer answer = new Answer(form, user,this);
        answers.add(answer);

        return answer;
    }
}