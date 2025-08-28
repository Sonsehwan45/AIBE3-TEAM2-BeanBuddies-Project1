package com.back.jsb.dto;

import jakarta.validation.constraints.NotBlank;

public record AnswerForm (
        @NotBlank
        String content
){
}