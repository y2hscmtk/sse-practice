package com.choi76.sse.domain.member.entity;

import com.choi76.sse.global.jwt.dto.Authority;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String loginId;
    private String password;
    @Enumerated(EnumType.STRING)
    private Authority authority;
}
