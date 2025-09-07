package com.choi76.sse.domain.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequestDto {
    @NotNull(message = "로그인 아이디 입력은 필수입니다.")
    @Email
    private String loginId;
    @NotNull(message = "패스워드 입력은 필수입니다.")
    private String password;
}
