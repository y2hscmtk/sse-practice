package com.choi76.sse.global.config;

import com.choi76.sse.global.jwt.filter.JWTFilter;
import com.choi76.sse.global.jwt.handler.CustomAccessDeniedHandler;
import com.choi76.sse.global.jwt.handler.CustomAuthenticationEntryPoint;
import com.choi76.sse.global.jwt.service.CustomUserDetailsService;
import com.choi76.sse.global.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // 메서드 수준의 보안 활성화
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // csrf disable
        http
                .csrf(AbstractHttpConfigurer::disable);
        // Form 로그인 방식, http basic 인증 방식 disable
        http
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable);

        // JWT 검증 필터를 UsernamePasswordAuthenticationFilter 앞에 추가
        http
                .addFilterBefore(new JWTFilter(customUserDetailsService, jwtUtil),
                        UsernamePasswordAuthenticationFilter.class);

        // 시큐리티 예외처리 필터
        http
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler));

        // 경로별 인가 설정
        http
                .authorizeHttpRequests(auth -> auth
                        // login, root, join 경로의 요청에 대해서는 모두 허용
                        .requestMatchers("/api/member/login", "/api/member/join").permitAll()
                        // 이외의 요청에 대해서는 인증된 사용자만 허용
                        .anyRequest().authenticated()
                );
        // JWT 방식에서 세션은 STATELESS 상태로 관리됨
        http
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }


}
