package com.shop.api.interceptor;


import com.shop.api.biz.system.service.AuthTokenService;
import com.shop.api.biz.system.service.JwtService;
import com.shop.core.annotations.NotAuthRequired;
import com.shop.core.entity.AuthToken;
import com.shop.core.entity.User;
import com.shop.core.enums.ApiResultCode;
import com.shop.core.enums.JwtSessionAttribute;
import com.shop.core.vo.response.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

/**
 * <pre>
 * Description: 인증 Interceptor
 * Date: 2023/01/26 12:35 PM
 * Company: smart
 * Author: luckeey
 * </pre>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtService jwtService;

    private final AuthTokenService authTokenService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String uri = request.getRequestURI();
        //log.debug(">>>>>> request uri = {}", request.getRequestURI());

        // BAD URI 요청의 경우 인증체크 하지 않도록 처리
        if(uri.equals("/error")) {
            return true;
        }

        if (handler instanceof HandlerMethod)
        {
            HandlerMethod method = (HandlerMethod) handler;

            // 호출대상 핸들러가 로그인이 필요한 경우(AccessToken 필요)
            if (!method.hasMethodAnnotation(NotAuthRequired.class))
            {
                String accessToken = request.getHeader("Authorization");

                // 인증토큰이 없으면 에러처리
                if(!StringUtils.hasLength(accessToken)) {
                    log.warn(">>>>>> {}", ApiResultCode.NOT_FOUND_TOKEN.getResultMessage());
                    response.getWriter().write(new ApiResponse<>(ApiResultCode.NOT_FOUND_TOKEN).toString());
                    return false;
                }

                // 앞에 Bearer 붙어서 오면 제거
                accessToken = accessToken.replace("Bearer ", "");

                //log.debug(">>>>> Request AccessToken = [{}]", accessToken);

                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setStatus(HttpServletResponse.SC_OK);
                response.setCharacterEncoding(StandardCharsets.UTF_8.name());
                if(!StringUtils.hasLength(accessToken)) {                  // 토큰이 없는 경우 에러
                    response.getWriter().write(new ApiResponse<>(ApiResultCode.NOT_FOUND_TOKEN).toString());
                    return false;
                } else if (!jwtService.isValidToken(accessToken)) {     // 토큰 유효성 체크
                    response.getWriter().write(new ApiResponse<>(ApiResultCode.TOKEN_UNAVAILABLE).toString());
                    return false;
                }

                User user = jwtService.getUser(accessToken);

                //log.debug(">>>>>>>> Interceptor::User = {}", user);

                // 필요한 정보를 세팅
                request.setAttribute(JwtSessionAttribute.ACCESS_TOKEN.name(), accessToken);
                request.setAttribute(JwtSessionAttribute.USER_ID.name(), user.getId());
                request.setAttribute(JwtSessionAttribute.USER_LOGIN_ID.name(), user.getLoginId());
                request.setAttribute(JwtSessionAttribute.USER.name(), user);

                // 토큰의 위/변조 및 ExpireTime 체크
                int resultCode = checkTokenAndLifeExpire(user.getId(), accessToken);
                if(resultCode < 0) {
                    if(resultCode == -1) {
                        response.getWriter().write(new ApiResponse<>(ApiResultCode.ACCESS_TOKEN_EXPIRED).toString());
                        return false;
                    } else if (resultCode == -2) {
                        response.getWriter().write(new ApiResponse<>(ApiResultCode.TOKEN_EXPIRED).toString());
                        return false;
                    } else if (resultCode == -3) {
                        response.getWriter().write(new ApiResponse<>(ApiResultCode.TOKEN_UNAVAILABLE).toString());
                        return false;
                    }
                }
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        // TODO
    }

    /**
     * 토큰의 위/변조 및 ExpireTime 체크
     * @param userId
     * @param accessToken
     * @return
     */
    private int checkTokenAndLifeExpire(Integer userId, String accessToken) {

        LocalDateTime now = LocalDateTime.now();

        // 회원의 인증정보 조회
        AuthToken authToken = authTokenService.selectAuthTokenByUserId(userId);

        // AccessToken 위/변조 체크
        if(authToken == null || !accessToken.equals(authToken.getAccessToken())) {
            //return -3;
            return 0;
        }

        // AccessToken Expire 체크
        if( now.isAfter(authToken.getAccessTokenExpireDateTime()) ) {
            return -1;
        } else if ( now.isAfter(authToken.getRefreshTokenExpireDateTime()) ) {
            return -2;
        }

        return 0;
    }
}
