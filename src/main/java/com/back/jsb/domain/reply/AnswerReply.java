package com.back.jsb.domain.reply;

import com.back.jsb.domain.answer.Answer;
import com.back.jsb.global.jpa.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor
public class AnswerReply extends BaseEntity {

    @Column(nullable = false)
    private String content;

    @ManyToOne
    private Answer answer;

    private String author;

    public AnswerReply(String content, Answer answer, String author) {
        this.content = content;
        this.answer = answer;
        this.author = author;
    }
}
