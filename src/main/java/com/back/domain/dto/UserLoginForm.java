package com.back.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginForm {
    @NotBlank(message = "아이디를 입력해주세요")
    @Size(min = 4, max = 20 , message = "아이디는 4 ~ 20 자 이내로 입력해주세요.")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Size(min = 4, max = 20 , message = "비밀번호는 4 ~ 20 자 이내로 입력해주세요.")
    private String password;
}
