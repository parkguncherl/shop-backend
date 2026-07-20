package com.shop.core.frontWeb.dao;

import com.shop.core.frontWeb.vo.response.PageViewHistoryResponse;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PageViewHistoryDao {

    private final SqlSessionTemplate sqlSession;
    private static final String NAMESPACE = "com.shop.mapper.frontWeb.PageViewHistoryMapper.";

    public List<PageViewHistoryResponse.RecentProduct> selectRecentProducts(String guestId) {
        return sqlSession.selectList(NAMESPACE + "selectRecentProducts", guestId);
    }
}
