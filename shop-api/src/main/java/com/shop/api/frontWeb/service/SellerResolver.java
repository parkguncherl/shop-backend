package com.shop.api.frontWeb.service;

import com.shop.core.biz.system.dao.UserDao;
import com.shop.core.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * FO 스토어프론트: 요청 host(Origin/Host) 로 셀러(User)를 식별한다.
 * TB_USER.domain(콤마 구분 목록)에 host 가 포함된 셀러를 매칭.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SellerResolver {

    private final UserDao userDao;

    /** 로컬(개발) 환경 기본 셀러 user_id */
    private static final Integer LOCAL_DEFAULT_USER_ID = 3;

    /** 요청 host 로 매칭된 셀러(User) 반환. 없으면 null. */
    public User resolveSeller(HttpServletRequest request) {
        String host = extractHost(request);
        if (StringUtils.isEmpty(host)) {
            return null;
        }
        return userDao.selectSellerByDomain(host);
    }

    /** 요청 host 로 매칭된 셀러의 user_id 반환. 로컬(localhost) 은 기본값(3), 없으면 null. */
    public Integer resolveUserId(HttpServletRequest request) {
        String host = extractHost(request);
        if (isLocalHost(host)) {
            return LOCAL_DEFAULT_USER_ID;
        }
        if (StringUtils.isEmpty(host)) {
            return null;
        }
        User seller = userDao.selectSellerByDomain(host);
        return seller != null ? seller.getId() : null;
    }

    private boolean isLocalHost(String host) {
        if (StringUtils.isEmpty(host)) {
            return false;
        }
        return "localhost".equalsIgnoreCase(host)
                || "127.0.0.1".equals(host)
                || "0:0:0:0:0:0:0:1".equals(host)
                || "::1".equals(host);
    }

    private String extractHost(HttpServletRequest request) {
        String origin = request.getHeader("Origin");
        String host = null;
        if (StringUtils.isNotEmpty(origin)) {
            host = origin.replaceFirst("^https?://", "").split("/")[0].split(":")[0];
        }
        if (StringUtils.isEmpty(host)) {
            host = request.getServerName();
        }
        return host;
    }
}
