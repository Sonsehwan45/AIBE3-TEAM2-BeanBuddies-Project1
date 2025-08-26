package com.back.domain.entity;

import com.back.global.jpa.entity.BaseEntity;
import jakarta.persistence.Entity;

@Entity
public class Question extends BaseEntity {
    private String title;
    private String content;
}