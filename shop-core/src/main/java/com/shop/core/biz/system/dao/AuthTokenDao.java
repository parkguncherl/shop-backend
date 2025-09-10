package com.shop.core.biz.system.dao;


import com.shop.core.entity.AuthToken;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;


/**
 * <pre>
 * Description: JWT 인증토큰 Dao
 * Date: 2022-06-04 오후 1:33
 * Company: TWORUN SOFT
 * Author : hyong
 * </pre>
 */
@Repository
@RequiredArgsConstructor
public class AuthTokenDao {

    private final String PRE_NS = "com.shop.mapper.authToken.";


    @Qualifier("sqlSessionTemplate")
    private final SqlSession sqlSession;


    /**
     * 인증토큰 조회 (by userId)
     * @param userId
     * @return
     */
    public AuthToken selectAuthTokenByUserId(Integer userId) {
        return sqlSession.selectOne(PRE_NS.concat("selectAuthTokenByUserId"), userId);
    }

    /**
     * 인증토큰 조회 (by accessToken)
     * @param accessToken
     * @return
     */
    public AuthToken selectAuthTokenByAccessToken(String accessToken) {
        return sqlSession.selectOne(PRE_NS.concat("selectAuthTokenByAccessToken"), accessToken);
    }

    /**
     * 인증토큰 등록
     * @param authToken
     * @return
     */
    public Integer createAuthToken(AuthToken authToken){
        return sqlSession.insert(PRE_NS.concat("createAuthToken"), authToken);
    }

    /**
     * 인증토큰 폐기
     * @param personId
     * @param accessToken
     * @return
     */
    public Integer updateTokenByLogout(Integer personId, String accessToken){
        Map<String,Object> filter = new HashMap<>();
        filter.put("personId", personId);
        filter.put("accessToken", accessToken);

        return sqlSession.insert(PRE_NS.concat("updateTokenByLogout"), filter);
    }


    /**
     * 인증토큰 완전 폐기
     * @param userId
     * @return
     */
    public Integer deleteAuthTokenByAccessToken(Integer userId){
        return sqlSession.selectOne(PRE_NS.concat("deleteAuthTokenByAccessToken"), userId);
    }

}
