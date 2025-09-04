package com.back.jsb.domain.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetForm {
    @NotBlank(message = "아이디를 입력해주세요.")
    private String username;
}
