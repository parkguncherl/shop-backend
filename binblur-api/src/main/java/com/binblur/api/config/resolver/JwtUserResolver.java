package com.binblur.api.config.resolver;

import com.binblur.api.annotation.JwtUser;
import com.binblur.core.entity.User;
import com.binblur.core.enums.JwtSessionAttribute;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import jakarta.servlet.http.HttpServletRequest;

/**
 * <pre>
 * Description : JWT 유저 리졸버
 * Date : 2023/02/14 10:41
 * Company : smart
 * Author : kdonghwa
 * </pre>
 */
@Configuration
public class JwtUserResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(JwtUser.class);
    }

    @Override
    public User resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        return (User) request.getAttribute(JwtSessionAttribute.USER.name());
    }
}