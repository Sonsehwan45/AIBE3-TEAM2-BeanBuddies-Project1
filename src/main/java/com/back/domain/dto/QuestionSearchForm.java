package com.back.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionSearchForm {
    private String searchType;
    private String keyword;
}
