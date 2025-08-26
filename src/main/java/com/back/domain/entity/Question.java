package com.back.domain.entity;

import com.back.global.jpa.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Question extends BaseEntity {
    private String title;
    private String content;

    public Question(String title, String content) {
        this.title = title;
        this.content = content;
    }
}