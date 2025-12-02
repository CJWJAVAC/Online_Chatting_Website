package com.example.chatApp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    // 1) SecurityFilterChain: HttpSecurity 구성 (WebSecurityConfigurerAdapter 대신)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 처리: 개발/테스트 시에는 disable 해도 되지만 운영에서는 주의 필요
        http.csrf(csrf -> csrf.disable());

        // headers/frameOptions sameOrigin 설정 (iframe 관련)
        http.headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

        // formLogin 사용 (권한 없는 접속시 로그인 폼으로)
        http.formLogin(Customizer.withDefaults());

        // authorize 설정: antMatchers -> requestMatchers 사용
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/chat/**").hasRole("USER")   // /chat/** 은 USER 권한 필요
                .anyRequest().permitAll()
        );

        return http.build();
    }

    // 2) In-memory 사용자 계정 (테스트용)
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user1 = User.withUsername("happydaddy")
                .password(passwordEncoder.encode("1234"))
                .roles("USER")
                .build();

        UserDetails user2 = User.withUsername("angrydaddy")
                .password(passwordEncoder.encode("1234"))
                .roles("USER")
                .build();

        UserDetails guest = User.withUsername("guest")
                .password(passwordEncoder.encode("1234"))
                .roles("GUEST")
                .build();

        return new InMemoryUserDetailsManager(user1, user2, guest);
    }

    // 3) PasswordEncoder 빈 (권장 방식)
    @Bean
    public PasswordEncoder passwordEncoder() {
        // DelegatingPasswordEncoder를 반환 -> "{bcrypt}..." 등 다양한 인코딩 지원
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
