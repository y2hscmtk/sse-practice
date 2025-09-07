package com.choi76.sse.domain.member.dto;

import com.choi76.sse.global.jwt.dto.Authority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoResponse {
    private String loginId;
    private Authority authority;
}
