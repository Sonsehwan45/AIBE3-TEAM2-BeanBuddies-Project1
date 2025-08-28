package com.back.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionSearchDto {
    private String searchType;
    private String keyword;
}
