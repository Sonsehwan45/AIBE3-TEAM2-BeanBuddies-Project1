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
public class AnswerForm {
        private Integer id;

        @NotBlank(message = "내용을 입력해주세요.")
        @Size(min = 2, max = 100, message = "답변은 2 ~ 100 자 이내로 입력해주세요.")
        private String content;

        private Integer QuestionId;
}