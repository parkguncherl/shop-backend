package com.shop.core.frontWeb.dao;

import com.shop.core.entity.GuestReferrerLog;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GuestReferrerLogDao {

    private final SqlSessionTemplate sqlSession;
    private static final String NAMESPACE = "com.shop.mapper.frontWeb.GuestReferrerLog.";

    /** 유입 경로 이력 적재 */
    public int insertReferrerLog(GuestReferrerLog log) {
        return sqlSession.insert(NAMESPACE + "insertReferrerLog", log);
    }

    /** 게스트 토큰 ID로 이력 조회 */
    public List<GuestReferrerLog> selectLogsByGuestTokenId(Long guestTokenId) {
        return sqlSession.selectList(NAMESPACE + "selectLogsByGuestTokenId", guestTokenId);
    }
}
