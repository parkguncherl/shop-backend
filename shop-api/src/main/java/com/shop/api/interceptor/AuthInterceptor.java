package com.shop.api.interceptor;

import com.shop.api.biz.system.service.AuthTokenService;
import com.shop.api.biz.system.service.JwtService;
import com.shop.api.config.JwtTokenProvider;
import com.shop.api.frontWeb.service.GuestTokenService;
import com.shop.core.annotations.NotAuthRequired;
import com.shop.core.entity.AuthToken;
import com.shop.core.entity.User;
import com.shop.core.enums.ApiResultCode;
import com.shop.core.enums.GlobalConst;
import com.shop.core.enums.JwtSessionAttribute;
import com.shop.core.vo.response.ApiResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtService jwtService;
    private final JwtTokenProvider  jwtTokenProvider;
    private final AuthTokenService authTokenService;
    private final GuestTokenService guestTokenService;  // ← 추가


    @Override
    public boolean preHandle(HttpServletRequest request,
                             @NotNull HttpServletResponse response,
                             @NotNull Object handler) throws Exception {

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) return true;

        String uri = request.getRequestURI();
        if (uri.equals("/error")) return true;

        if (handler instanceof HandlerMethod method) {  // Java 21 패턴매칭

            if (!method.hasMethodAnnotation(NotAuthRequired.class)) {
                // ─── 기존 회원 인증 로직 그대로 ──────────────────
                String accessToken = request.getHeader("Authorization");

                if (!StringUtils.hasLength(accessToken)) {
                    log.warn(">>>>>> {}", ApiResultCode.NOT_FOUND_TOKEN.getResultMessage());
                    response.getWriter().write(new ApiResponse<>(ApiResultCode.NOT_FOUND_TOKEN).toString());
                    return false;
                }

                accessToken = accessToken.replace("Bearer ", "");

                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setStatus(HttpServletResponse.SC_OK);
                response.setCharacterEncoding(StandardCharsets.UTF_8.name());

                if (!StringUtils.hasLength(accessToken)) {
                    response.getWriter().write(new ApiResponse<>(ApiResultCode.NOT_FOUND_TOKEN).toString());
                    return false;
                } else if (!jwtService.isValidToken(accessToken)) {
                    response.getWriter().write(new ApiResponse<>(ApiResultCode.TOKEN_UNAVAILABLE).toString());
                    return false;
                }

                User user = jwtService.getUser(accessToken);

                request.setAttribute(JwtSessionAttribute.ACCESS_TOKEN.name(), accessToken);
                request.setAttribute(JwtSessionAttribute.USER_ID.name(), user.getId());
                request.setAttribute(JwtSessionAttribute.USER_LOGIN_ID.name(), user.getLoginId());
                request.setAttribute(JwtSessionAttribute.USER.name(), user);

                int resultCode = checkTokenAndLifeExpire(user.getId(), accessToken);
                if (resultCode < 0) {
                    if (resultCode == -1) {
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

            }  else {
                // FO API Guest Token 검증
                if (uri.startsWith("/shop-ap/frontWeb")) {
                    String guestToken = request.getHeader("X-Guest-Token");
                    log.debug(">>>>>> X-Guest-Token 헤더: {}", guestToken);

                    if (guestToken == null) {
                        Cookie[] cookies = request.getCookies();
                        log.debug(">>>>>> 쿠키 개수: {}", cookies != null ? cookies.length : 0);
                        if (cookies != null) {
                            for (Cookie cookie : cookies) {
                                log.debug(">>>>>> 쿠키: {} = {}", cookie.getName(), cookie.getValue());
                                if (GlobalConst.GUEST_TOKEN.getCode().equals(cookie.getName())) {
                                    guestToken = cookie.getValue();
                                    break;
                                }
                            }
                        }
                    }
                    log.debug(">>>>>> 최종 guestToken: {}", guestToken);

                    if (!StringUtils.hasLength(guestToken)) {
                        writeError(response, ApiResultCode.NOT_FOUND_TOKEN);
                        return false;
                    }

                    if (!jwtTokenProvider.validateGuestToken(guestToken)) {
                        writeError(response, ApiResultCode.TOKEN_UNAVAILABLE);
                        return false;
                    }

                    String guestId = jwtTokenProvider.getGuestId(guestToken);

                    // PostgreSQL Rate Limiting
                    if (!guestTokenService.checkRateLimit(guestId)) {
                        log.warn(">>>>>> Rate Limit 초과 : {}", guestId);
                        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                        response.setStatus(429);
                        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
                        response.getWriter().write(
                                """
                                {"resultCode":429,"resultMessage":"요청 횟수를 초과했습니다."}
                                """
                        );
                        return false;
                    }

                    String shopId     = jwtTokenProvider.getShopId(guestToken);  // ← 추가
                    request.setAttribute("GUEST_ID", guestId);
                    request.setAttribute("SHOP_ID",  shopId);
                }
            }
        }

        return true;
    }

    // ─── 기존 토큰 만료 체크 그대로 ───────────────────────────
    private int checkTokenAndLifeExpire(Integer userId, String accessToken) {
        LocalDateTime now = LocalDateTime.now();
        AuthToken authToken = authTokenService.selectAuthTokenByUserId(userId);
        if (authToken == null || !accessToken.equals(authToken.getAccessToken())) {
            return 0;
        }
        if (now.isAfter(authToken.getAccessTokenExpireDateTime())) {
            return -1;
        } else if (now.isAfter(authToken.getRefreshTokenExpireDateTime())) {
            return -2;
        }
        return 0;
    }

    private void writeError(
            HttpServletResponse response,
            ApiResultCode resultCode) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(
                new ApiResponse<>(resultCode).toString()
        );
    }
}