package com.ssm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Profile({"local", "dev"})
public class SecurityConfig {
	
    @Bean
    protected SecurityFilterChain swaggerSecurityFilterChain(HttpSecurity http) throws Exception {
    	http
    	.csrf(AbstractHttpConfigurer::disable)
    	.authorizeHttpRequests(auth -> auth.requestMatchers(
                "/swagger-ui/**",
                "/v3/api-docs/**",
                "/swagger-resources/**",
                "/webjars/**",                     // ✅ 이거 추가!
                "/swagger-ui.html",                // ✅ 추가!
                "/swagger-ui.html/**"           // ✅ 혹시 몰라 명시!
            ).permitAll()
    			.anyRequest().authenticated() // 그 외 경로는 허용
            )
    	 .formLogin(Customizer.withDefaults()) // 기본 로그인 페이지 사용
         .httpBasic(Customizer.withDefaults()); // Swagger가 쓰는 Basic Auth 대응
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
