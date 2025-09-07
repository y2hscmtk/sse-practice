package com.choi76.sse.global.jwt.service;

import com.choi76.sse.domain.member.entity.Member;
import com.choi76.sse.domain.member.repository.MemberRepository;
import com.choi76.sse.global.enums.statuscode.ErrorStatus;
import com.choi76.sse.global.exception.GeneralException;
import com.choi76.sse.global.jwt.dto.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// userDetails를 생성하여 반환
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId)
            throws UsernameNotFoundException {
        // UserDetails 객체 생성 -> JWT 검증시 사용
        Member member = memberRepository.findMemberByLoginId(loginId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));
        return new AuthUser(member);
    }
}
