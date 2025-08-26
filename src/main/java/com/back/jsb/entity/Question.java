package com.back.jsb.entity;

import com.back.jsb.entity.base.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.Getter;

@Entity
@Getter
public class Question extends BaseEntity {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    @Column(columnDefinition = "TEXT")
    private List<Answer> answers;

}