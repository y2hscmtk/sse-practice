package com.choi76.sse.domain.member.dto;

import com.choi76.sse.global.jwt.dto.Authority;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateAuthorityRequest {
    private Authority authority;
}
