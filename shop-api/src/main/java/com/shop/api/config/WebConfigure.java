package com.shop.api.config;

import com.shop.api.config.resolver.GuestUserResolver;
import com.shop.api.config.resolver.JwtUserResolver;
import com.shop.api.interceptor.AuthInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

/**
 * <pre>
 * Description: 웹MVC 설정
 * Date: 2023/01/26 12:35 PM
 * Company: smart
 * Author: luckeey
 * </pre>
 */
@Configuration
@RequiredArgsConstructor
public class WebConfigure implements WebMvcConfigurer {

    @Value("${cors.endpoint.url}")
    private String corsUrls;

    private final AuthInterceptor authInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/cw/**", "/v3/api-docs/**", "/v3/api-docs.yaml", "/swagger-ui.html")
                .excludePathPatterns("/assets/**", "/css/**", "/js/**", "/images/**", "/fonts/**", "/components/**", "/webjars/**")
                .excludePathPatterns("/frontWebAuth/**");  // ← 추가
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String[] patterns = corsUrls.split("\\s*,\\s*");

        // 💡 프로토콜이 붙은 와일드카드(https://*.도메인)에서 프로토콜 부분을 날려주는 안전장치 추가
        for (int i = 0; i < patterns.length; i++) {
            if (patterns[i].contains("*.")) {
                patterns[i] = patterns[i].replaceAll("https?://", "");
            }
        }

        registry.addMapping("/**")
                .allowedOriginPatterns(patterns)
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                .allowedHeaders(
                        "Origin",
                        "X-Requested-With",
                        "Content-Type",
                        "Accept",
                        "Authorization",
                        "token",
                        "Content-Disposition",
                        "X-Guest-Token",
                        "Cookie"            // ← 추가
                )
                .exposedHeaders(
                        "Origin",
                        "X-Requested-With",
                        "Content-Type",
                        "Accept",
                        "Authorization",
                        "token",
                        "Content-Disposition",
                        "X-Guest-Token",
                        "Set-Cookie"        // ← 추가
                );
    }
    /**
     * 메시지 번들이 Loading 하는 폴더위치 및 파일명 지정
     *
     * @return
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:/messages/message");   // messages = 경로, message = 파일명
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }


    /**
     * 커스텀 어노테이션 리졸버 추가 처리
     * @param resolvers initially an empty list
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new JwtUserResolver());
        resolvers.add(new GuestUserResolver());  // ← 추가
    }

    @Bean
    public LocaleResolver localeResolver() {
        // default locale 설정
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(Locale.KOREA);
        return sessionLocaleResolver;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        registrar.setDateFormatter(DateTimeFormatter.ofPattern("yy-MM-dd"));
        registrar.registerFormatters(registry);
    }
}