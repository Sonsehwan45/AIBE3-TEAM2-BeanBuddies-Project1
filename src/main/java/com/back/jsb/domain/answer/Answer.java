package com.back.jsb.domain.answer;

import com.back.jsb.domain.question.Question;
import com.back.jsb.domain.user.User;
import com.back.jsb.global.jpa.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Answer extends BaseEntity {
    @Column(nullable = false)
    private String content;

    @ManyToOne
    private User author;

    @ManyToOne
    private Question question;

    public Answer(AnswerForm form, User author, Question question) {
        this.author = author;
        this.content = form.getContent();
        this.question = question;
    }
}