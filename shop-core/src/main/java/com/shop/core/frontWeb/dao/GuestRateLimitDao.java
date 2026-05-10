package com.shop.core.frontWeb.dao;

import com.shop.core.entity.GuestToken;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GuestTokenDao {

    private final SqlSessionTemplate sqlSession;
    private static final String NAMESPACE = "com.shop.mapper.frontWeb.GuestTokenMapper.";


    public int insertGuestToken(GuestToken guestToken) {
        return sqlSession.insert(NAMESPACE + "insertGuestToken", guestToken);
    }

    public GuestToken selectGuestTokenByGuestId(String guestId) {
        return sqlSession.selectOne(NAMESPACE + "selectGuestTokenByGuestId", guestId);
    }

    public int deleteExpiredGuestToken() {
        return sqlSession.delete(NAMESPACE + "deleteExpiredGuestToken");
    }
}