package com.example.mes_backend.config;

import com.example.mes_backend.service.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security 설정을 담당하는 클래스
 */
@AllArgsConstructor // 모든 필드를 인자로 받는 생성자를 자동으로 생성
@Configuration // 이 클래스를 Spring 설정 파일로 지정
@EnableWebSecurity // 자동 구성과의 충돌을 방지
public class SecurityConfig {

    // JWT 토큰을 다루는 JwtProvider를 주입
    private final JwtProvider jwtProvider;

    @Bean // 이 메서드가 반환하는 객체를 Spring Bean으로 등록
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                // CORS 설정 활성화 -프론트엔드와 백엔드가 도메인이 다를 경우 필요
                .cors(Customizer.withDefaults())
                // JWT를 사용하므로 CSRF 보호 비활성화
                .csrf(csrf ->csrf.disable())
                // JWT 기반 인증을 위해 세션 사용 안 함
                .sessionManagement(
                        sess ->sess.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )
                // 요청 경로에 대한 접근 권한 설정
                .authorizeHttpRequests(
                        auth -> auth.requestMatchers(
                                        // 누구나 접근 가능한 '공개' 경로 설정
                                        "/api/auth/login",
                                        // api모든접근허용
                                        "/api/**"
                                ).permitAll()
                                // 특정 역할(ROLE)을 가진 사용자만 접근 허용
//                                .requestMatchers("/api/v1/admin", "/api/v1/admin/**").hasRole("ADMIN")
//                                .requestMatchers("/api/v1/worker", "/api/v1/worker/**").hasRole("WORKER") // worker 전용 API 경로
//                                .requestMatchers("/api/v1/manager", "/api/v1/manager/**").hasRole("MANAGER") // manager 전용 API 경로
                                // 그 외 모든 요청은 인증 필요
                                .anyRequest().authenticated()
                )
                // 커스텀 JWT 필터를 기본 인증 필터 이전에 추가
                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtProvider),
                        UsernamePasswordAuthenticationFilter.class
                );
        return http.build();
    }

    // 비밀번호 암호화를 위한 BCryptPasswordEncoder 빈 등록
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}