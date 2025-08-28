package com.back.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AnswerForm {
    @NotBlank(message = "내용을 입력해주세요.")
    @Size(min = 2, max = 100, message = "내용은 2 ~ 100 자 이내로 입력해주세요.")
    String content;
}