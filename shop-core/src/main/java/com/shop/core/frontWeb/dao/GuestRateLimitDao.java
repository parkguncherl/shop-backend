package com.shop.core.frontWeb.dao;

import com.shop.core.entity.GuestRateLimit;
import com.shop.core.entity.GuestToken;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GuestRateLimitDao {

    private final SqlSessionTemplate sqlSession;
    private static final String NAMESPACE = "com.shop.mapper.frontWeb.GuestRateLimitMapper.";

    public GuestRateLimit selectRateLimit(GuestRateLimit guestRateLimit) {
        return sqlSession.selectOne(NAMESPACE + "selectRateLimit", guestRateLimit);
    }

    public int insertRateLimit(GuestRateLimit guestRateLimit) {
        return sqlSession.insert(NAMESPACE + "insertRateLimit", guestRateLimit);
    }

    public int incrementRateLimit(GuestRateLimit guestRateLimit) {
        return sqlSession.update(NAMESPACE + "incrementRateLimit", guestRateLimit);
    }

    public int deleteOldRateLimits() {
        return sqlSession.delete(NAMESPACE + "deleteOldRateLimits");
    }
}