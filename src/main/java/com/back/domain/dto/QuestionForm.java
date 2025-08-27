//클라이언트(사용자)가 작성한 질문 데이터를 담아 컨트롤러로 전달
package com.back.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class QuestionForm {
    @NotBlank(message = "제목을 입력해주세요.")
    @Size(min = 2, max = 50, message = "제목은 2 ~ 50 자 이내로 입력해주세요.")
    String title;

    @NotBlank(message = "내용을 입력해주세요.")
    @Size(min = 2, max = 100, message = "내용은 2 ~ 100 자 이내로 입력해주세요.")
    String content;
}