package com.binblur.api.config;

import com.binblur.api.config.resolver.JwtUserResolver;
import com.binblur.api.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.List;
import java.util.Locale;

/**
 * <pre>
 * Description : 웹MVC 설정
 * Date : 2023/01/26 12:35 PM
 * Company : smart
 * Author : luckeey
 * </pre>
 */
@Configuration
public class WebConfigure implements WebMvcConfigurer {

    @Value("${cors.endpoint.url}")
    private String corsUrls;

    @Autowired
    private AuthInterceptor authInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/cw/**", "/v3/api-docs/**", "/v3/api-docs.yaml", "/swagger-ui.html")
                .excludePathPatterns("/assets/**", "/css/**", "/js/**", "/images/**", "/fonts/**", "/components/**", "/webjars/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(corsUrls)
                .exposedHeaders("*")
                .allowCredentials(true)
                .allowedMethods("*")
                .allowedHeaders("*");
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
    }

    @Bean
    public LocaleResolver localeResolver() {
        // default locale 설정
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(Locale.KOREA);
        return sessionLocaleResolver;
    }
}