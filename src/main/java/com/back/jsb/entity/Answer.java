package com.back.jsb.entity;

import com.back.jsb.entity.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Answer extends BaseEntity {

    @NotBlank
    private String content;

    @ManyToOne
    private Question question;

    @ManyToOne
    private SiteUser author;

    public void update(String content) {
        this.content = content;
    }
}