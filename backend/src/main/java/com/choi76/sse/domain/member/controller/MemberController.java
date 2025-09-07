package com.choi76.sse.domain.member.controller;

import com.choi76.sse.domain.member.dto.JoinDto;
import com.choi76.sse.domain.member.dto.LoginRequestDto;
import com.choi76.sse.domain.member.dto.MemberInfoResponse;
import com.choi76.sse.domain.member.service.MemberService;
import com.choi76.sse.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    /**
     * 회원 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto loginRequestDTO) {
        return memberService.login(loginRequestDTO);
    }

    /**
     * 회원 가입
     */
    @PostMapping("/join")
    public ResponseEntity<?> join(@Valid @RequestBody JoinDto joinDTO) {
        return memberService.join(joinDTO);
    }

    /**
     * 내 정보 조회
     */
    @GetMapping("/me")
    public ApiResponse<MemberInfoResponse> getMyInfo() {
        return ApiResponse.onSuccess(memberService.getMemberInfo());
    }
}
