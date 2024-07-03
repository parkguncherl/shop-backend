package com.binblur.api.config;

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
 * Description : Swagger 설정
 * Swagger EndPoint : http://localhost:8080/binblur-ap/swagger-ui/index.html
 * Date : 2023/02/15 10:15 PM
 * Company : smart
 * Author : luckeey
 * </pre>
 */
@Configuration
public class SwaggerConfigure {
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("smart")
                .pathsToMatch("/**")
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
                        .title("Smart API") // 타이틀
                        .version(appVersion) // 문서 버전
                        .description("") // 문서 설명
                        .contact(new Contact() // 연락처
                                .name("")
                                .email("")
                                .url("")));
    }
}