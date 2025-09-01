package com.back.jsb.domain.answer;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AnswerForm {

    @NotBlank(message = "내용을 써주세요.")
    private String content;

    public AnswerForm(Answer answer) {
        setContent(answer.getContent());
    }
}