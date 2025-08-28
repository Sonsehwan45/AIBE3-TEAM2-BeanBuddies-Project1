package com.back.jsb.entity;

import com.back.jsb.entity.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Answer extends BaseEntity {

    @NotBlank
    private String content;

    @ManyToOne
    private Question question;

    public Answer(String content, Question question) {
        this.content = content;
        this.question = question;
    }
}