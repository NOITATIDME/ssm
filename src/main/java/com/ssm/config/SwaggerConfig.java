package com.ssm.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
@Profile({"local", "dev"})
public class SwaggerConfig {

    @Bean
    protected OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("My API")
                .version("1.0")
                .description("API 문서입니다.")
                //.contact(new Contact().name("관리자").email("admin@example.com"))
            ).components(new Components()
                    .addSecuritySchemes("basicAuth", // ⬅️ 보안 설정 - Swagger UI에 표시됨
                            new SecurityScheme()
                                    .type(SecurityScheme.Type.HTTP) // ⬅️ HTTP 인증 방식
                                    .scheme("basic") // ⬅️ basic 인증 스킴 (username/password)
                    )
            ).addSecurityItem(new SecurityRequirement().addList("basicAuth")); // ⬅전체 API에 인증 요구
            
    }
    
    @Bean
    protected GroupedOpenApi apiGroup() {
        return GroupedOpenApi.builder()
                .group("ssm") // ⬅️ API 그룹 이름 (문서의 제목처럼 사용됨)
                .packagesToScan("com.ssm") // ⬅️ 이 패키지 하위의 컨트롤러만 문서화
                .build();
    }
}
