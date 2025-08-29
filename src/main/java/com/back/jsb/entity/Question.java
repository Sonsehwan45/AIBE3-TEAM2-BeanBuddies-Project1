package com.back.jsb.entity;

import com.back.jsb.entity.base.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Question extends BaseEntity {

    private String title;

    private String content;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    @Column(columnDefinition = "TEXT")
    private List<Answer> answers = new ArrayList<>();

    @ManyToOne
    private SiteUser author;

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Question(String title, String content, SiteUser author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }
}