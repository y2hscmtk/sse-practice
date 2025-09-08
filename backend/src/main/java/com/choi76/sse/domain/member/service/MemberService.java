package com.choi76.sse.domain.member.service;

import com.choi76.sse.domain.member.dto.JoinDto;
import com.choi76.sse.domain.member.dto.LoginRequestDto;
import com.choi76.sse.domain.member.dto.MemberInfoResponse;
import com.choi76.sse.domain.member.dto.UpdateAuthorityRequest;
import com.choi76.sse.domain.member.entity.Member;
import com.choi76.sse.domain.member.repository.MemberRepository;
import com.choi76.sse.global.enums.statuscode.ErrorStatus;
import com.choi76.sse.global.exception.GeneralException;
import com.choi76.sse.global.jwt.dto.AuthUser;
import com.choi76.sse.global.jwt.dto.Authority;
import com.choi76.sse.global.jwt.util.JwtUtil;
import com.choi76.sse.global.response.ApiResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // 로그인
    @Transactional
    public ResponseEntity<?> login(LoginRequestDto dto) {
        String loginId = dto.getLoginId();
        String password = dto.getPassword();
        Member member = memberRepository.findMemberByLoginId(loginId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        // 비밀번호 검증
        if(!passwordEncoder.matches(password, member.getPassword())) {
            throw new GeneralException(ErrorStatus.PASSWORD_NOT_CORRECT);
        }

        String accessToken = jwtUtil.createJwt(member);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken); // JWT 발급 성공시 Header에 삽입하여 반환

        return ResponseEntity.ok().headers(headers)
                .body(ApiResponse.onSuccess("Bearer " + accessToken));
    }

    public ResponseEntity<?> join(JoinDto joinDTO) {
        // 동일 username 사용자 생성 방지
        if (memberRepository.existsMemberByLoginId(joinDTO.getLoginId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(ApiResponse.onFailure(ErrorStatus._MEMBER_IS_EXISTS, "회원가입에 실패하였습니다."));
        }

        Member member = Member.builder()
                .loginId(joinDTO.getLoginId())
                .password(passwordEncoder.encode(joinDTO.getPassword())) // 암호화 후 저장
                .authority(Authority.ROLE_USER)
                .build();
        memberRepository.save(member);

        String accessToken = jwtUtil.createJwt(member);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        return ResponseEntity.ok().headers(headers)
                .body(ApiResponse.onSuccess("Bearer " + accessToken));
    }

    public MemberInfoResponse getMemberInfo(AuthUser authUser) {
        Member member = authUser.getMember();

        return MemberInfoResponse.builder()
                .loginId(member.getLoginId())
                .authority(member.getAuthority())
                .build();
    }

    public List<MemberInfoResponse> getAllMembers() {
        return memberRepository.findAll().stream()
                .map(Member::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public MemberInfoResponse updateAuthority(Long memberId, UpdateAuthorityRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));
        member.updateAuthority(request.getAuthority());
        return member.toDto();
    }
}
