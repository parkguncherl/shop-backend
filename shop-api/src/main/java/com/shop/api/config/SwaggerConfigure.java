package com.shop.api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

/**
 * <pre>
 * Description: Swagger 설정
 * Swagger EndPoint : g
 * Date: 2023/02/15 10:15 PM
 * Company: smart
 * Author: luckeey
 * </pre>
 */
@Configuration
public class SwaggerConfigure {
    // ─── 기존 전체 API 그룹 (frontWeb 제외) ──────────────
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("backOffice")
                .pathsToMatch("/**")
                .pathsToExclude("/frontWeb/**")  // ← frontWeb 제외
                .build();
    }

    // ─── FO 전용 그룹 추가 ────────────────────────────
    @Bean
    public GroupedOpenApi frontWebApi() {
        return GroupedOpenApi.builder()
                .group("frontWeb")
                .pathsToMatch("/frontWeb/**")  // ← frontWeb 으로 시작하는 URI만
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI(@Value("${springdoc.version}") String appVersion) {
        SecurityRequirement addSecurityItem = new SecurityRequirement();
        addSecurityItem.addList("JWT");

        return new OpenAPI()
                .components(new Components().addSecuritySchemes("JWT", new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .in(SecurityScheme.In.HEADER)
                        .name(HttpHeaders.AUTHORIZATION)))
                .addSecurityItem(addSecurityItem)
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("smart-shop-api") // 타이틀
                        .version(appVersion) // 문서 버전
                        .description("") // 문서 설명
                        .contact(new Contact() // 연락처
                                .name("")
                                .email("")
                                .url("")));
    }
}