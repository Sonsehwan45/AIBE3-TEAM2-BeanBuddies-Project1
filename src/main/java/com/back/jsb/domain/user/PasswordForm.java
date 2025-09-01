package com.back.jsb.domain.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordForm {
    private String oldPassword;
    private String password;
    private String confirmPassword;
}
