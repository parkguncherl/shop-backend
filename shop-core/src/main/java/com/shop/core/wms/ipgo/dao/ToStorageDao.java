package com.shop.core.wms.ipgo.dao;

import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.entity.Inven;
import com.shop.core.wms.ipgo.vo.request.ToStorageRequest;
import com.shop.core.wms.ipgo.vo.response.ToStorageResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 적치정보 DAO
 */

@Repository
@RequiredArgsConstructor
public class ToStorageDao {

    private final SqlSession sqlSession;
    private static final String NAMESPACE = "com.binblur.mapper.wms.ipgo.ToStorageMapper";

    /**
     * 적치 목록 페이징 조회
     * @param pageRequest 필터값
     * @return 적치목록
     */
    public PageResponse<ToStorageResponse.Paging> selectToStorageListPaging(PageRequest<ToStorageRequest.PagingFilter> pageRequest) {
        List<ToStorageResponse.Paging> instocks = sqlSession.selectList(NAMESPACE.concat(".selectToStorageListPaging"), pageRequest);
        if (ObjectUtils.isNotEmpty(instocks)) {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount(), instocks, instocks.get(0).getTotalRowCount());
        } else {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount());
        }
    }

    /**
     * 상품 적치 목록 조회
     * @param request prodId, skuId
     * @return 상품적치목록
     */
    public List<ToStorageResponse.ProdLocInfo> selectProdLocList(ToStorageRequest.ProdLoc request) {
        return sqlSession.selectList(NAMESPACE.concat(".selectProdLocList"), request);
    }


    /**
     * 적치 전표 상세 조회
     * @param request 발주ID
     * @return 적치 전표 정보
     */
    public ToStorageResponse.PrintDetail selectToStoragePrintDetail(ToStorageRequest.PrintDetail request) {
        return sqlSession.selectOne(NAMESPACE.concat(".selectToStoragePrintDetail"), request);
    }

    /**
     * 적치 전표 상세 품목 조회
     * @param request 발주ID
     * @return 전표 상세 품목 목록
     */
    public List<ToStorageResponse.DetailItem> selectToStoragePrintDetailItems(ToStorageRequest.PrintDetail request) {
        return sqlSession.selectList(NAMESPACE.concat(".selectToStoragePrintDetailItems"), request);
    }

    /**
     * 입고정보 조회
     * @param request stockId
     * @return 입고정보
     */
    public ToStorageResponse.StockInfo selectStockInfo(Map<String, Object> request) {
        return sqlSession.selectOne(NAMESPACE.concat(".selectStockInfo"), request);
    }

    /**
     * 적치 재고수량 조회
     * @param request stockId ..
     * @return 적치수량
     */
    public Integer selectCountInvenByStockId(Map<String, Object> request) {
        return sqlSession.selectOne(NAMESPACE.concat(".selectCountInvenByStockId"), request);
    }

    /**
     * 새로운 재고 정보를 배치 등록합니다.
     * @param invenList 등록할 재고 정보 리스트
     */
    public int insertInvenBatch(List<Inven> invenList) {
        return sqlSession.insert(NAMESPACE.concat(".insertInvenBatch"), invenList);
    }

    /**
     * 적치 상태코드 정보를 수정합니다.
     * @param params
     * @return
     */
    public int updateStockStatCd(Map<String, Object> params) {
        return sqlSession.update(NAMESPACE.concat(".updateStockStatCd"), params);
    }

    /**
     * 새로운 매장재고를 등록합니다
     * @param request 등록할 재고 정보 리스트
     */
    public int createMejangInven(ToStorageRequest.Create request) {
        return sqlSession.insert(NAMESPACE.concat(".createMejangInven"), request);
    }
}