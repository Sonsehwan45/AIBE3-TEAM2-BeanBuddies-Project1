package com.back.jsb.domain.reply;


import jakarta.validation.constraints.NotBlank;

public record ReplyRegisterForm (
        @NotBlank
        String content
) {
}
