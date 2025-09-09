package com.shop.core.wms.ipgo.dao;

import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.entity.Asn;
import com.shop.core.entity.AsnGroup;
import com.shop.core.entity.Stock;
import com.shop.core.wms.ipgo.vo.request.InstockRequest;
import com.shop.core.wms.ipgo.vo.response.InstockResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 입하정보 DAO
 */

@Repository
@RequiredArgsConstructor
public class InstockDao {

    private final SqlSession sqlSession;
    private static final String NAMESPACE = "com.binblur.mapper.wms.ipgo.InstockMapper";

    /**
     * 입하 목록을 페이징하여 조회
     * @param pageRequest 필터값
     * @return 입하목록
     */
    public PageResponse<InstockResponse.Paging> selectInstockListPaging(PageRequest<InstockRequest.PagingFilter> pageRequest) {
        List<InstockResponse.Paging> instocks = sqlSession.selectList(NAMESPACE.concat(".selectInstockListPaging"), pageRequest);
        if (ObjectUtils.isNotEmpty(instocks)) {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount(), instocks, instocks.get(0).getTotalRowCount());
        } else {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount());
        }
    }

    /**
     * 입하 목록 통계 대시보드 조회
     * @param logisId 창고ID
     * @return 입하수량관련 데이타
     */
    public InstockResponse.StatDashBoard selectInstockStatForDashBoard(Integer logisId) {
        return sqlSession.selectOne(NAMESPACE.concat(".selectInstockStatForDashBoard"), logisId);
    }

    /**
     * 입하 전표 상세 조회
     * @param request 그룹핑 조회 조건
     * @return 전표 상세 데이타
     */
    public InstockResponse.PrintDetail selectInstockDetail(InstockRequest.Detail request) {
        return sqlSession.selectOne(NAMESPACE.concat(".selectInstockDetail"), request);
    }

    /**
     * 입하 상세 아이템 목록 조회
     * @param request 그룹핑 조회 조건
     * @return 전표 상세 아이템 목록
     */
    public List<InstockResponse.DetailItem> selectInstockDetailItems(InstockRequest.Detail request) {
        return sqlSession.selectList(NAMESPACE.concat(".selectInstockDetailItems"), request);
    }

    /**
     * 입하 목록 By ASN IDs
     * @param asnIds 입하아이디 리스트
     * @return 입하목록
     */
    public List<Asn> selectInstockListByAsnIds(Map<String, Object> asnIds) {
        return sqlSession.selectList(NAMESPACE.concat(".selectInstockListByAsnIds"), asnIds);
    }

    /**
     * 새로운 입하 정보를 등록합니다.
     * @param stock 등록할 입하 정보
     */
    public int insertStock(Stock stock) {
        return sqlSession.insert(NAMESPACE.concat(".insertStock"), stock);
    }

    /**
     * 발주 상태코드 정보를 수정합니다.
     * @param params
     * @return
     */
    public int updateAsnStatCd(Map<String, Object> params) {
        return sqlSession.update(NAMESPACE.concat(".updateAsnStatCd"), params);
    }

    /**
     * 발주 그룹을 등록합니다.
     * @param asnGroup
     */
    public int insertAsnGroup(AsnGroup asnGroup) {
        return sqlSession.insert(NAMESPACE.concat(".insertAsnGroup"), asnGroup);
    }

    /**
     * 기타입하 등록건을 삭제합니다.
     * @param delete
     */
    public int deleteInstock(InstockRequest.Delete delete) {
        return sqlSession.update(NAMESPACE.concat(".deleteInstock"), delete);
    }

    /**
     * 대리입하 등록건을 삭제합니다.
     * @param delete
     */
    public int deleteRepAsn(InstockRequest.Delete delete) {
        return sqlSession.update(NAMESPACE.concat(".deleteRepAsn"), delete);
    }

    /**
     * 입하 조회 By ASN ID
     * @param id 아이디
     * @return 입하
     */
    public InstockResponse.StockInfo selectStockByAsnId(Integer id) {
        return sqlSession.selectOne(NAMESPACE.concat(".selectStockByAsnId"), id);
    }

    /**
     * 입하 정산 처리 수정  (배치)
     * @param params 수정할 입고 ID, 정산유무값(Y/N)
     */
    public int updateStockTranStatus(Map<String, Object> params) {
        return sqlSession.insert(NAMESPACE.concat(".updateStockTranStatus"), params);
    }

    /**
     * 입하목록(매장분반납) 조회 (페이징)
     */
    public PageResponse<InstockResponse.ReturnPaging> getInstockReturnListPaging(PageRequest<InstockRequest.PagingFilter> pageRequest) {
        List<InstockResponse.ReturnPaging> instocks = sqlSession.selectList(NAMESPACE.concat(".getInstockReturnListPaging"), pageRequest);
        if (ObjectUtils.isNotEmpty(instocks)) {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount(), instocks, instocks.get(0).getTotalRowCount());
        } else {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount());
        }
    }

    /**
     * 생산처 발주 입하처리 상세 내역 조회
     */
    public List<InstockResponse.InstockFactoryAsnDetail> selectInstockFactoryAsnDetail(InstockRequest.Detail request) {
        return sqlSession.selectList(NAMESPACE.concat(".selectInstockFactoryAsnDetail"), request);
    }

    /**
     * 매장분 반납 입하처리 상세 내역 조회
     */
    public List<InstockResponse.InstockReturnDetail> getInstockReturnDetail(InstockRequest.ReturnDetail request) {
        return sqlSession.selectList(NAMESPACE.concat(".getInstockReturnDetail"), request);
    }

    /**
     *  매장분반납 입하 전표 인쇄 상세 조회
     */
    public InstockResponse.PrintDetail selectInstockReturnDetail(InstockRequest.ReturnDetail request) {
        return sqlSession.selectOne(NAMESPACE.concat(".selectInstockReturnDetail"), request);
    }

    /**
     *  매장분반납 입하 전표 인쇄 상세 아이템 조회
     *  @param request 그룹핑 조회 조건
     *  @return 전표 상세 아이템 목록
     */
    public List<InstockResponse.DetailItem> selectInstockReturnDetailItems(InstockRequest.ReturnDetail request) {
        return sqlSession.selectList(NAMESPACE.concat(".selectInstockReturnDetailItems"), request);
    }

    /**
     *  발주그룹 카운트 조회
     */
    public Integer selectAsnGroupCount(Integer asnId) {
        return sqlSession.selectOne(NAMESPACE.concat(".selectAsnGroupCount"), asnId);
    }
}