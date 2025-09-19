package com.shop.api.biz.system.service;

import com.shop.core.biz.system.dao.AuthTokenDao;
import com.shop.core.entity.AuthToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <pre>
 * Description: JWT 인증토큰 Service
 * Date: 2023/01/26 12:35 PM
 * Company: smart90
 * Author: luckeey
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthTokenService {


    private final AuthTokenDao authTokenDao;


    /**
     * 인증토큰 조회 (by userId)
     * @param userId
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public AuthToken selectAuthTokenByUserId(Integer userId) {
        return authTokenDao.selectAuthTokenByUserId(userId);
    }

    /**
     * 인증토큰 조회 (by accessToken)
     * @param accessToken
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public AuthToken selectAuthTokenByAccessToken(String accessToken) {
        return authTokenDao.selectAuthTokenByAccessToken(accessToken);
    }

    /**
     * 인증토큰 등록
     * @param authToken
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer createAuthToken(AuthToken authToken) {
        return authTokenDao.createAuthToken(authToken);
    }

    /**
     * 인증토큰 폐기
     * @param userId
     * @param accessToken
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer updateTokenByLogout(Integer userId, String accessToken) {
        return authTokenDao.updateTokenByLogout(userId, accessToken);
    }


    /**
     * 인증토큰 완전 폐기
     * @param userId
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer deleteAuthTokenByAccessToken(Integer userId) {
        return authTokenDao.deleteAuthTokenByAccessToken(userId);
    }

}
