package com.choi76.sse.domain.member.repository;

import com.choi76.sse.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Boolean existsMemberByLoginId(String loginId);
    Optional<Member> findMemberByLoginId(String loginId);
}
