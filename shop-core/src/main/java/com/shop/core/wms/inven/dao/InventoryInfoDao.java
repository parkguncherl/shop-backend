package com.shop.core.wms.inven.dao;

import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
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
public class InventoryInfoDao {
    private final SqlSession sqlSession;
    private static final String NAMESPACE = "com.binblur.mapper.wms.inven.InventoryInfoMapper.";

    /**
     * 상품 목록을 SKU 정보와 함께 조회합니다.
     *
     * @param pageRequest 페이징 및 필터 정보
     * @return 상품 목록 및 페이징 정보
     */
    public PageResponse<InventoryInfoResponse.Paging> selectInventoryInfoSkuPaging(PageRequest<InventoryInfoRequest.PagingFilter> pageRequest) {
        List<InventoryInfoResponse.Paging> products = sqlSession.selectList(NAMESPACE + "selectInventoryInfoSkuPaging", pageRequest);
        int totalRowCount = products == null || products.isEmpty() ? 0 : products.size();
        return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount(), products, totalRowCount);
    }

    /**
     * 위치별 재고 정보 목록 조회
     *
     * @param id 재고 ID
     * @return 위치별 재고 정보 목록
     */
    public List<InventoryInfoResponse.Detail> selectInventoryLocationList(Integer id) {
        return sqlSession.selectList(NAMESPACE + "selectInventoryLocationList", id);
    }
    /**
     * 상품 목록을 SKU 정보와 함께 조회합니다.
     *
     * @param pageRequest 페이징 및 필터 정보
     * @return 상품 목록 및 페이징 정보
     */
    public PageResponse<InventoryInfoResponse.Paging> selectInventoryInfoLocPaging(PageRequest<InventoryInfoRequest.PagingFilter> pageRequest) {
//        Integer totalRowCount = sqlSession.selectOne(NAMESPACE + "getInventoryListTotalLoc", pageRequest);
        List<InventoryInfoResponse.Paging> products = sqlSession.selectList(NAMESPACE + "selectInventoryInfoLocPaging", pageRequest);
        int totalRowCount = products.isEmpty() ? 0 : products.get(0).getTotalRowCount();
        return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount(), products, totalRowCount);
    }
    /**
     * 스큐별 재고 정보 목록 조회
     *
     * @param id 재고 ID
     * @return 스큐별 재고 정보 목록
     */
    public List<InventoryInfoResponse.Detail> selectLocationSkuList(Integer id) {
        return sqlSession.selectList(NAMESPACE + "selectLocationSkuList", id);
    }


    /**
     * 스큐별 재고 개수
     *
     * @param skuId, locId 재고 ID
     * @return 스큐별 재고 정보 목록
     */
    public Integer selectInvenCountSpecialLocation(Integer skuId, Integer locId) {
        Map<String, Object> params = new HashMap<>();
        params.put("skuId", skuId);
        params.put("locId", locId);
        return sqlSession.selectOne(NAMESPACE + "selectInvenCountSpecialLocation", params);
    }

    /**
     * 스큐별 재고 수정
     *
     * @param updateInvenLocation
     * @return 스큐별 재고 정보 수정
     */
    public Integer wmsChangeLocation(InventoryInfoRequest.UpdateInvenLocation updateInvenLocation) {
        return sqlSession.update(NAMESPACE + "wmsChangeLocation", updateInvenLocation);
    }



    /**
     * 실사 목록을 SKU 정보와 함께 조회합니다.
     *
     * @param pageRequest 페이징 및 필터 정보
     * @return 실사 목록 및 페이징 정보
     */
    public PageResponse<InspectionInfoResponse.Paging> selectInspectionPaging(PageRequest<InspectInfoRequest.PagingFilter> pageRequest) {
        List<InspectionInfoResponse.Paging> products = sqlSession.selectList(NAMESPACE + "selectInspectionPaging", pageRequest);
        int totalRowCount = products == null || products.isEmpty() ? 0 : products.size();
        return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount(), products, totalRowCount);
    }


    /**
     * 로케이션별 재고 목록을 조회합니다.
     *
     * @param filter  필터 정보
     * @return 재고 목록 정보
     */
    public List<InventoryInfoResponse.InventoryLocationListResponse> selectInvenLocationList(InventoryInfoRequest.InventoryLocationFilter filter) {
        return sqlSession.selectList(NAMESPACE + "selectInvenLocationList", filter);
    }



    /**
     * 로케이션별 재고 목록을 조회합니다.
     *
     * @param filter  필터 정보
     * @return 재고 목록 정보
     */
    public List<InventoryInfoResponse.SkuLocationInfo> selectInventoryLocationDetailList(InventoryInfoRequest.InventoryLocationDetFilter filter) {
        return sqlSession.selectList(NAMESPACE + "selectInventoryLocationDetailList", filter);
    }

}