package com.back.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateForm {
    @NotBlank(message = "아이디를 입력해주세요.")
    @Size(min = 4, max = 20 , message = "아이디는 4 ~ 20 자 이내로 입력해주세요.")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 4, max = 20 , message = "비밀번호는 4 ~ 20 자 이내로 입력해주세요.")
    private String password;

    @NotBlank(message = "비밀번호 확인를 입력해주세요")
    @Size(min = 4, max = 20 , message = "비밀번호는 4 ~ 20 자 이내로 입력해주세요.")
    private String passwordCheck;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "올바른 이메일 형식을 입력해주세요.")
    private String email;
}