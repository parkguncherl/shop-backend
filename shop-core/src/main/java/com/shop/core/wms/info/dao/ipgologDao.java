package com.shop.core.wms.info.dao;

import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.entity.Inven;
import com.shop.core.wms.info.vo.request.ipgologRequest;
import com.shop.core.wms.info.vo.response.ipgologResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 적치정보 DAO
 */

@Repository
@RequiredArgsConstructor
public class ipgologDao {

    private final SqlSession sqlSession;
    private static final String NAMESPACE = "com.binblur.mapper.wms.info.ipgologMapper";

    /**
     * 적치 목록을 페이징하여 조회
     * @param pageRequest 필터값
     * @return 적치목록
     */
    public PageResponse<ipgologResponse.Paging> ipgologPaging(PageRequest<ipgologRequest.PagingFilter> pageRequest) {
        List<ipgologResponse.Paging> instocks = sqlSession.selectList(NAMESPACE.concat(".ipgologPaging"), pageRequest);
        if (ObjectUtils.isNotEmpty(instocks)) {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount(), instocks, instocks.get(0).getTotalRowCount());
        } else {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount());
        }
    }
}