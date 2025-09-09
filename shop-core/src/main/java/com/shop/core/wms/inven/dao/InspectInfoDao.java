package com.shop.core.wms.inven.dao;

import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.entity.Inspect;
import com.shop.core.entity.InspectDet;
import com.shop.core.wms.inven.vo.request.InspectInfoRequest;
import com.shop.core.wms.inven.vo.request.InventoryInfoRequest;
import com.shop.core.wms.inven.vo.response.InspectionInfoResponse;
import com.shop.core.wms.inven.vo.response.InventoryInfoResponse;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class InspectInfoDao {
    private final SqlSession sqlSession;
    private static final String NAMESPACE = "com.binblur.mapper.wms.inven.InspectInfoMapper.";

    /**
     * 상품 목록을 SKU 정보와 함께 조회합니다.
     *
     * @param pageRequest 페이징 및 필터 정보
     * @return 상품 목록 및 페이징 정보
     */
    public PageResponse<InspectionInfoResponse.Paging> selectInspectPaging(PageRequest<InspectInfoRequest.PagingFilter> pageRequest) {
        List<InspectionInfoResponse.Paging> products = sqlSession.selectList(NAMESPACE + "selectInspectPaging", pageRequest);
        int totalRowCount = products == null || products.isEmpty() ? 0 : products.size();
        return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount(), products, totalRowCount);
    }

    public int insertInspect(Inspect inspect) {
        return sqlSession.insert(NAMESPACE + "insertInspect", inspect);
    }

    public int updateInspect(Inspect inspect) {
        return sqlSession.insert(NAMESPACE + "updateInspect", inspect);
    }

    public int insertInspectDet(InspectDet inspectDet) {
        return sqlSession.insert(NAMESPACE + "insertInspectDet", inspectDet);
    }

    public int updateInspectDet(InspectDet inspectDet) {
        return sqlSession.insert(NAMESPACE + "updateInspectDet", inspectDet);
    }

    public int selectInspectionByUk(Integer skuId, Integer locId) {
        Map<String, Object> params = new HashMap<>();
        params.put("skuId", skuId);
        params.put("locId", locId);
        return sqlSession.selectOne(NAMESPACE + "selectInspectionByUk", params);
    }

    public Inspect selectInspectById(Integer id) {
        return sqlSession.selectOne(NAMESPACE + "selectInspectById", id);
    }

    public List<InspectionInfoResponse.Detail> selectInspectDetList(Integer inspectId) {
        return sqlSession.selectList(NAMESPACE + "selectInspectDetList", inspectId);
    }
}