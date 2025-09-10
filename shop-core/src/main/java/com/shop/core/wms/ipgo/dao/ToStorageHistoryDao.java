package com.shop.core.wms.ipgo.dao;

import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.wms.ipgo.vo.request.ToStorageHistoryRequest;
import com.shop.core.wms.ipgo.vo.response.ToStorageHistoryResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 적치이력정보 DAO
 */

@Repository
@RequiredArgsConstructor
public class ToStorageHistoryDao {

    private final SqlSession sqlSession;
    private static final String NAMESPACE = "com.shop.mapper.wms.ipgo.ToStorageHistoryMapper";

    /**
     * 적치이력 목록 페이징 조회
     * @param pageRequest 필터값
     * @return 적치목록
     */
    public PageResponse<ToStorageHistoryResponse.Paging> selectToStorageHistoryListPaging(PageRequest<ToStorageHistoryRequest.PagingFilter> pageRequest) {
        List<ToStorageHistoryResponse.Paging> instocks = sqlSession.selectList(NAMESPACE.concat(".selectToStorageHistoryListPaging"), pageRequest);
        if (ObjectUtils.isNotEmpty(instocks)) {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount(), instocks, instocks.get(0).getTotalRowCount());
        } else {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount());
        }
    }

    /**
     * 해당 재고 출고이력 수 조회
     * @param request
     * @return 출고이력수
     */
    public Integer selectCountInvenOutHist(ToStorageHistoryRequest.Cancel request) {
        return sqlSession.selectOne(NAMESPACE.concat(".selectCountInvenOutHist"), request);
    }

    /**
     * 해당 재고 삭제
     * @param params
     * @return
     */
    public Integer deleteInvenByStockId(Map<String, Object> params) {
        return sqlSession.update(NAMESPACE.concat(".deleteInvenByStockId"), params);
    }

    /**
     * 적치 상태코드 정보를 수정합니다.
     * @param params
     * @return
     */
    public int updateStockStatCd(Map<String, Object> params) {
        return sqlSession.update(NAMESPACE.concat(".updateStockStatCd"), params);
    }

}