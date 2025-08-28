package com.back.domain.entity;

import com.back.global.jpa.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Answer extends BaseEntity {
    private String content;

    @ManyToOne
    private Question question;

    public Answer(String content, Question question) {
        this.content = content;
        this.question = question;
    }

    public void update(String content) {
        this.content = content;
    }
}
