package com.back.jsb.domain.question;

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
public class Question extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    private User author;

    public Question(QuestionForm form, User user) {
        this.title = form.getTitle();
        this.content = form.getContent();
        this.author = user;
    }
}