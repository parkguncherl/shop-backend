package com.shop.api.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.springframework.util.PatternMatchUtils;

/**
 * <pre>
 * Description: Cors 설정
 * Date: 2023/01/28 10:39 PM
 * Company: smart
 * Author: luckeey
 * </pre>
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {

    @Value("${cors.endpoint.url}")
    private String corsUrls;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String origin = request.getHeader("Origin");
        List<String> allowedOrigins = Arrays.stream(corsUrls.split(","))
                .map(String::trim)
                .filter(url -> !url.isEmpty())
                .toList();

        if (origin != null && isAllowedOrigin(origin, allowedOrigins)) {
            response.setHeader("Access-Control-Allow-Origin", origin);
        }

        response.setHeader("Vary", "Origin");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, PATCH, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers",
                "Origin, X-Requested-With, Content-Type, Accept, Authorization, token, Content-Disposition, X-Guest-Token, Cookie");
        response.setHeader("Access-Control-Expose-Headers",
                "Origin, X-Requested-With, Content-Type, Accept, Authorization, token, Content-Disposition, X-Guest-Token, Set-Cookie");
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(req, res);
        }
    }

    @Override
    public void destroy() {

    }

    private boolean isAllowedOrigin(String origin, List<String> allowedOrigins) {
        for (String allowedOrigin : allowedOrigins) {
            if (origin.equals(allowedOrigin)) {
                return true;
            }

            if (allowedOrigin.startsWith("*.")) {
                String domain = allowedOrigin.substring(1);
                if (origin.endsWith(domain)) {
                    return true;
                }
            }

            if (PatternMatchUtils.simpleMatch(allowedOrigin, origin)) {
                return true;
            }
        }

        return false;
    }
}
