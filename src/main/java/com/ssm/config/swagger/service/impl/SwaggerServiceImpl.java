package com.ssm.config.swagger.service.impl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ssm.config.swagger.service.SwaggerService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SwaggerServiceImpl implements SwaggerService {
	
	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // ✅ Spring Security가 이 메서드를 자동으로 호출함
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));

//		log.info("loadUserByUsername 진입 username : {}"+ username );

	    System.out.println("🔥 loadUserByUsername called with: " + username);

		
        if (!"admin".equals(username)) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username);
        }
		
        // ✅ 스프링이 이해할 수 있는 UserDetails 객체로 변환
        return User
                .withUsername("admin"/*user.getUsername()*/)
                .password("$2a$10$qHZp.ihCJpllYtYFzzLcmexs3WORg4OxOfN96XqebhBzHXGn1eLvK"/*user.getPassword()*/) // 👉 이건 이미 암호화된 값이어야 해요!
                .authorities("USER") // 👈 권한 (role) 부여
                .build();
    }
}
