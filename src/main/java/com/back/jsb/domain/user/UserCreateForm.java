package com.back.jsb.domain.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateForm {

    @NotBlank(message = "아이디는 필수 입력입니다.")
    private String username;

    @NotBlank(message = "비밀번호는 필수 입력입니다.")
    private String password;

    @NotBlank(message = "비밀번호 확인은 필수 입력입니다.")
    private String passwordConfirm;

    @NotBlank(message = "닉네임은 필수 입력입니다.")
    private String nickname;
}