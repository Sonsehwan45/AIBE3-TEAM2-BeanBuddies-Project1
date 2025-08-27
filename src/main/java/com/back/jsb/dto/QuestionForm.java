package com.back.jsb.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record QuestionForm(
        @NotBlank(message = "제목은 필수 입력 항목입니다.")
        @Size(max = 200, message = "제목은 200자를 초과할 수 없습니다.")
        String title,

        @NotBlank(message = "내용은 필수 입력 항목입니다.")
        String content
) {
}