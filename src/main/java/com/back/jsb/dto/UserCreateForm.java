package com.back.jsb.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserCreateForm(
        @NotBlank(message = "사용자 이름은 필수 입력 항목입니다.")
        @Size(min = 2, max = 10, message = "사용자 이름은 2자 이상 10자 이하로 입력해야 합니다.")
        String username,

        @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
        @Size(min = 4, max = 12, message = "비밀번호는 4자 이상 12자 이하로 입력해야 합니다.")
        String password,

        @NotBlank(message = "비밀번호 확인은 필수항목입니다.")
        String confirmPassword,

        @NotBlank(message = "이메일은 필수 입력 항목입니다.")
        @Email(message = "유효한 이메일 주소를 입력해야 합니다.")
        String email
) {
}