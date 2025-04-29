package com.ssm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Profile({"local", "dev"})
public class SecurityConfig {

    @Bean
    protected SecurityFilterChain swaggerSecurityFilterChain(HttpSecurity http) throws Exception {
    	http
        // rest api 설정
        .csrf(AbstractHttpConfigurer::disable) // csrf 비활성화 -> cookie를 사용하지 않으면 꺼도 된다. (cookie를 사용할 경우 httpOnly(XSS 방어), sameSite(CSRF 방어)로 방어해야 한다.)
        .cors(AbstractHttpConfigurer::disable) // cors 비활성화 -> 프론트와 연결 시 따로 설정 필요
        .httpBasic(AbstractHttpConfigurer::disable) // 기본 인증 로그인 비활성화
        .formLogin(AbstractHttpConfigurer::disable) // 기본 login form 비활성화
        .logout(AbstractHttpConfigurer::disable) // 기본 logout 비활성화
        .headers(c -> c.frameOptions(
                FrameOptionsConfig::disable).disable()) // X-Frame-Options 비활성화
        .sessionManagement(c ->
                c.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 사용하지 않음
        
    	.authorizeHttpRequests(auth -> auth.requestMatchers(
                "/swagger-ui/**",
                "/v3/api-docs/**",
                "/swagger-resources/**",
                "/webjars/**",                     // ✅ 이거 추가!
                "/swagger-ui.html",                // ✅ 추가!
                "/swagger-ui.html/**",           // ✅ 혹시 몰라 명시!
                "/index.html" // oauth2 임시용
            ).permitAll()
    			.anyRequest().authenticated() // 그 외 경로는 허용
            )
    	;

        return http.build(); // ⬅️ SecurityFilterChain Bean 반환
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // ⬅️ 비밀번호 암호화 방식 (DB 저장 시 사용) // 비밀번호 암호화 전략 (BCrypt)
    }

//    @Bean
//    protected AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//        return config.getAuthenticationManager();  // 인증 처리에 필요한 매니저
//
//    }
}
