package com.choi76.sse.domain.member.dto;

import com.choi76.sse.global.jwt.dto.Authority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JoinDto {
    private String loginId;
    private String password;
    private Authority authority;
}
