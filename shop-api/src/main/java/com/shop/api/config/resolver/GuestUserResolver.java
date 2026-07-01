package com.shop.api.config.resolver;

import com.shop.api.annotation.GuestUser;
import com.shop.core.entity.GuestToken;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class GuestUserResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(GuestUser.class)
                && parameter.getParameterType().equals(GuestToken.class);  // ← GuestToken 으로 변경
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) {

        HttpServletRequest request =
                (HttpServletRequest) webRequest.getNativeRequest();

        GuestToken guestToken = new GuestToken();
        guestToken.setGuestId((String)    request.getAttribute("GUEST_ID"));
        guestToken.setPartnerId((Integer) request.getAttribute("PARTNER_ID"));
        guestToken.setClientIp((String)   request.getAttribute("CLIENT_IP"));  // JWT claim 에서 추출된 값
        // 필요한 필드만 세팅

        return guestToken;
    }
}
