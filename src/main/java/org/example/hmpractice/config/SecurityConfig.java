package org.example.hmpractice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // 패스워드 인코더
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Spring Security 브라우저 접근 시 로그인 -> 비활성화 처리
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화 (POST요청 가능)
                .authorizeRequests(auth -> auth.anyRequest().permitAll()) // 모든 요청 허용(나중에 로그인 시큐리티 제대로 팔때 설정해보기)
                .formLogin(form -> form.disable()) // 로그인 폼 비활성화
                .httpBasic(httpBasic -> httpBasic.disable()); // 기본 인증 비활성화

        return http.build();
    }
}
