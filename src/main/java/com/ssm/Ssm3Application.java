package com.ssm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = "com.ssm")
public class Ssm3Application extends SpringBootServletInitializer {
	
	public static void main(String[] args) {
        try {
            SpringApplication.run(Ssm3Application.class, args);
        } catch (Exception e) {
            e.printStackTrace();  // 예외 상세 확인
        }

		/* SpringApplication.run(Ssm3Application.class, args); */
	}

}
