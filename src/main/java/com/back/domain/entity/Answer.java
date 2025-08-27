package com.back.domain.entity;

import com.back.global.jpa.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Answer extends BaseEntity {
    private String content;

    public Answer(String content) {
        this.content = content;
    }
}