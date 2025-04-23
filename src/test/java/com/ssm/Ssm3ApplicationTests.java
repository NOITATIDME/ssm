package com.ssm;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class Ssm3ApplicationTests {

	@Test
	void changePasswordEncode() {
        String rawPassword = "1234"; // 여기에 원래 비밀번호 입력
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println("🔐 Encoded Password: " + encodedPassword);
	}

}
