package com.shop.core.wms.ipgo.dao;

import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.entity.InvenChg;
import com.shop.core.entity.InvenLocChgLog;
import com.shop.core.wms.ipgo.vo.request.InvenMoveRequest;
import com.shop.core.wms.ipgo.vo.response.InvenMoveResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 재고이동 DAO
 */

@Repository
@RequiredArgsConstructor
public class InvenMoveDao {

    private final SqlSession sqlSession;
    private static final String NAMESPACE = "com.shop.mapper.wms.ipgo.InvenMoveMapper";

    /**
     * 재고이동 목록을 페이징하여 조회
     * @param pageRequest 필터값
     * @return 재고이동목록
     */
    public PageResponse<InvenMoveResponse.Paging> selectInvenMoveListPaging(PageRequest<InvenMoveRequest.PagingFilter> pageRequest) {
        List<InvenMoveResponse.Paging> invens = sqlSession.selectList(NAMESPACE.concat(".selectInvenMoveListPaging"), pageRequest);
        if (ObjectUtils.isNotEmpty(invens)) {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount(), invens, invens.get(0).getTotalRowCount());
        } else {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount());
        }
    }

    /**
     * 재고수량 목록 By SKU IDs
     * @param skuIds SKU ID 리스트
     * @return 재고수량 목록
     */
    public List<InvenMoveResponse.InvenCount> selectInvenCntListBySkuIds(Map<String, Object> skuIds) {
        return sqlSession.selectList(NAMESPACE.concat(".selectInvenCntListBySkuIds"), skuIds);
    }

    /**
     * 새로운 재고이동 이력 정보를 등록합니다.
     * @param invenLocChgLog 등록할 재고이동 정보
     */
    public int insertInvenLocChgLog(InvenLocChgLog invenLocChgLog) {
        return sqlSession.insert(NAMESPACE.concat(".insertInvenLocChgLog"), invenLocChgLog);
    }

    /**
     * 재고 이동 정보를 수정합니다.
     * @param params
     * @return
     */
    public int updateInvenLoc(Map<String, Object> params) {
        return sqlSession.update(NAMESPACE.concat(".updateInvenLoc"), params);
    }


    /**
     * 새로운 재고이동 이력 정보를 등록합니다.
     * @param invenChg 등록할 재고이동 정보
     */
    public int insertInvenChg(InvenChg invenChg) {
        return sqlSession.insert(NAMESPACE.concat(".insertInvenChg"), invenChg);
    }

}