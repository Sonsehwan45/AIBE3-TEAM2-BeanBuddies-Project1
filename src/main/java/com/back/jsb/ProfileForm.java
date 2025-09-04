package com.back.jsb;

import com.back.jsb.domain.user.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public record ProfileForm (
        MultipartFile profileImage,
        @NotBlank(message = "닉네임을 입력해주세요.")
        @Size(max=10, message ="닉네임은 10자 이하여야 합니다.")
        String nickname,
        boolean deleteProfileImage
){
    public ProfileForm(User user) {
        this(
                null,
                user.getNickname(),
                false
        );
    }
}
