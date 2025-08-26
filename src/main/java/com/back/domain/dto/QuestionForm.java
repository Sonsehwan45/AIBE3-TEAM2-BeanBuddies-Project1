package com.back.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuestionForm {
    private Integer id;

    @NotBlank(message = "제목을 입력해주세요.")
    @Size(min = 2, max = 20, message = "제목은 2 ~ 20 자 이내로 입력해주세요.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    @Size(min = 2, max = 100, message = "내용은 2 ~ 100 자 이내로 입력해주세요.")
    private String content;

    public QuestionForm(String title, String content) {
        this.title = title;
        this.content = content;
    }
}