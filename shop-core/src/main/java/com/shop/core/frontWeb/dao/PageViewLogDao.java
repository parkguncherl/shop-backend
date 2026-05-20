package com.shop.core.frontWeb.dao;

import com.shop.core.entity.PageViewLog;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PageViewLogDao {

    private final SqlSessionTemplate sqlSession;
    private static final String NAMESPACE = "com.shop.mapper.frontWeb.PageViewLogMapper.";

    public int insertPageViewLog(PageViewLog pageViewLog) {
        return sqlSession.insert(NAMESPACE + "insertPageViewLog", pageViewLog);
    }

    public int updatePageViewLog(PageViewLog pageViewLog) {
        return sqlSession.update(NAMESPACE + "updatePageViewLog", pageViewLog);
    }

    public PageViewLog selectPageViewLogById(Long id) {
        return sqlSession.selectOne(NAMESPACE + "selectPageViewLogById", id);
    }
}
