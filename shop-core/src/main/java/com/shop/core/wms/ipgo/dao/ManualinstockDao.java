package com.shop.core.wms.ipgo.dao;

import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.wms.ipgo.vo.request.ManualinstockRequest;
import com.shop.core.wms.ipgo.vo.response.ManualinstockResponse;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ManualinstockDao {

    private final SqlSession sqlSession;
    private static final String NAMESPACE = "com.shop.mapper.wms.ipgo.ManualinstockMapper";

    /**
     * 기타 입하 목록을 페이징하여 조회
     */
    public PageResponse<ManualinstockResponse.Paging> selectManualInStockPaging(PageRequest<ManualinstockRequest.PagingFilter> pageRequest) {
        List<ManualinstockResponse.Paging> manualInStocks = sqlSession.selectList(NAMESPACE.concat(".selectManualinstockListPaging"), pageRequest);
        if (manualInStocks != null && !manualInStocks.isEmpty()) {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount(), manualInStocks, manualInStocks.get(0).getTotalRowCount());
        } else {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount());
        }
    }
    /**
     * 화주 정보 검색
     */
    public List<ManualinstockResponse.Partner> searchPartners(ManualinstockRequest.PartnerSearchFilter filter) {
        return sqlSession.selectList(NAMESPACE + ".searchPartners", filter);
    }

    /**
     * SKU 정보 검색
     */
    public List<ManualinstockResponse.Sku> searchSkus(ManualinstockRequest.SkuSearchFilter filter) {
        return sqlSession.selectList(NAMESPACE + ".searchSkus", filter);
    }
    /**
     * 공장 정보 검색
     */
    public List<ManualinstockResponse.Factory> searchFactories(ManualinstockRequest.FactorySearchFilter filter) {
        return sqlSession.selectList(NAMESPACE + ".searchFactories", filter);
    }
    /**
     * ASN 정보 조회
     */
    public List<ManualinstockResponse.AsnInfo> searchAsnInfo(Map<String, Object> params) {
        return sqlSession.selectList(NAMESPACE + ".searchAsnInfo", params);
    }

    /**
     * ASN 상태 업데이트
     */
    public int updateAsnStatus(Integer asnId) {
        return sqlSession.update(NAMESPACE + ".updateAsnStatus", asnId);
    }

    /**
     * ASN 정보 생성
     */
    public int createAsn(ManualinstockRequest.Create request) {
        return sqlSession.insert(NAMESPACE + ".createAsn", request);
    }
    /**
     * 입고 정보 생성
     */
    public int createStock(ManualinstockRequest.Create request) {
        return sqlSession.insert(NAMESPACE + ".createStock", request);
    }

}
