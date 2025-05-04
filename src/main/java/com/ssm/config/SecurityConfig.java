package com.ssm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.ssm.auth.handler.OAuth2SuccessHandler;
import com.ssm.auth.service.CustomOAuth2UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService oAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
//    private final TokenAuthenticationFilter tokenAuthenticationFilter;

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
                "/index.html", // oauth2 임시용
                "/auth/success"
            ).permitAll()
    			.anyRequest().authenticated() // 그 외 경로는 허용
            )

    	 // oauth2 설정
        .oauth2Login(oauth -> // OAuth2 로그인 기능에 대한 여러 설정의 진입점
                // OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정을 담당
            	oauth.userInfoEndpoint(c -> c.userService(oAuth2UserService))
                        // 로그인 성공 시 핸들러
                        .successHandler(oAuth2SuccessHandler)
        )

        // jwt 관련 설정
//        .addFilterBefore(tokenAuthenticationFilter,
//                UsernamePasswordAuthenticationFilter.class)
//        .addFilterBefore(new TokenExceptionFilter(), tokenAuthenticationFilter.getClass()) // 토큰 예외 핸들링

        // 인증 예외 핸들링
//        .exceptionHandling((exceptions) -> exceptions
//                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
//                .accessDeniedHandler(new CustomAccessDeniedHandler()));

    	;

        return http.build(); // ⬅️ SecurityFilterChain Bean 반환
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // ⬅️ 비밀번호 암호화 방식 (DB 저장 시 사용) // 비밀번호 암호화 전략 (BCrypt)
    }

    @Bean
    protected WebSecurityCustomizer webSecurityCustomizer() { // security를 적용하지 않을 리소스
        return web -> web.ignoring()
                // error endpoint를 열어줘야 함, favicon.ico 추가!
                .requestMatchers("/error", "/favicon.ico","/images/**");
    }

//    @Bean
//    protected AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//        return config.getAuthenticationManager();  // 인증 처리에 필요한 매니저
//
//    }
}
