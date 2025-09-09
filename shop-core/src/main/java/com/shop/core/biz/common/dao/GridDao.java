package com.shop.core.biz.common.dao;

import com.shop.core.biz.common.vo.request.GridRequest;
import com.shop.core.biz.common.vo.response.GridResponse;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

/**
 * Description: 그리드 Dao
 */

@Repository
@RequiredArgsConstructor
public class GridDao {

    private final SqlSession sqlSession;
    private static final String NAMESPACE = "com.binblur.mapper.common.grid.";

    public Integer upsertGridColumn(GridRequest request) {
        return sqlSession.insert(NAMESPACE.concat("upsertGridColumn"), request);
    }

    public Integer deleteGridColum(GridRequest request) {
        return sqlSession.insert(NAMESPACE.concat("deleteGridColum"), request);
    }

    public GridResponse selectGridColum(GridRequest request) {
        return sqlSession.selectOne(NAMESPACE.concat("selectGridColum"), request);
    }
}
