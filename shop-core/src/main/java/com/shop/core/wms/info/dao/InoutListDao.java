package com.shop.core.wms.info.dao;

import com.shop.core.wms.info.vo.request.InoutListRequest;
import com.shop.core.wms.info.vo.response.InoutListResponse;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 적치정보 DAO
 */

@Repository
@RequiredArgsConstructor
public class InoutListDao {

    private final SqlSession sqlSession;
    private static final String NAMESPACE = "com.shop.mapper.wms.info.inoutMapper";

    /**
     * 적치 목록을 페이징하여 조회
     * @param filter 필터값
     * @return 적치목록
     */
    public List<InoutListResponse.InoutList> inoutList(InoutListRequest.InoutListFilter filter) {
        return sqlSession.selectList(NAMESPACE.concat(".inoutList"), filter);
    }
}