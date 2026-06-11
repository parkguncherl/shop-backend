package com.shop.core.frontWeb.dao;

import com.shop.core.entity.PointHistory;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PointDao {

    private final SqlSessionTemplate sqlSession;
    private static final String NAMESPACE = "com.shop.mapper.frontWeb.Point.";

    public Long selectPointBalance(Long socialAccountId) {
        return sqlSession.selectOne(NAMESPACE + "selectPointBalance", socialAccountId);
    }

    public int insertPointHistory(PointHistory pointHistory) {
        return sqlSession.insert(NAMESPACE + "insertPointHistory", pointHistory);
    }

    public List<PointHistory> selectPointHistories(Long socialAccountId) {
        return sqlSession.selectList(NAMESPACE + "selectPointHistories", socialAccountId);
    }
}
