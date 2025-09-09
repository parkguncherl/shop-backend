package com.shop.core.wms.ipgo.dao;

import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.wms.ipgo.vo.request.InstockHistoryRequest;
import com.shop.core.wms.ipgo.vo.response.InstockHistoryResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 입하이력 정보 DAO
 */

@Repository
@RequiredArgsConstructor
public class InstockHistoryDao {

    private final SqlSession sqlSession;
    private static final String NAMESPACE = "com.binblur.mapper.wms.ipgo.InstockHistoryMapper";

    /**
     * '발주' 입하이력목록 페이징 조회
     * @param pageRequest 필터값
     * @return 입하목록
     */
    public PageResponse<InstockHistoryResponse.FactoryPaging> selectInstockHistoryFactoryListPaging(PageRequest<InstockHistoryRequest.PagingFilter> pageRequest) {
        List<InstockHistoryResponse.FactoryPaging> instockHistorys = sqlSession.selectList(NAMESPACE.concat(".selectInstockHistoryFactoryListPaging"), pageRequest);
        if (ObjectUtils.isNotEmpty(instockHistorys)) {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount(), instockHistorys, instockHistorys.get(0).getTotalRowCount());
        } else {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount());
        }
    }

    /**
     * '매장분반납' 입하이력목록 조회 (페이징)
     * @param pageRequest 필터값
     * @return 입하목록
     */
    public PageResponse<InstockHistoryResponse.ReturnPaging> selectInstockHistoryReturnListPaging(PageRequest<InstockHistoryRequest.PagingFilter> pageRequest) {
        List<InstockHistoryResponse.ReturnPaging> instockHistorys = sqlSession.selectList(NAMESPACE.concat(".selectInstockHistoryReturnListPaging"), pageRequest);
        if (ObjectUtils.isNotEmpty(instockHistorys)) {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount(), instockHistorys, instockHistorys.get(0).getTotalRowCount());
        } else {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount());
        }
    }


    /**
     * '발주' 입하이력 미리보기 상세 조회
     * @param request 그룹핑 조회 조건
     * @return 미리보기 상세 데이타
     */
    public InstockHistoryResponse.PrintDetail selectInstockHistoryFactoryPreview(InstockHistoryRequest.FactoryDetail request) {
        return sqlSession.selectOne(NAMESPACE.concat(".selectInstockHistoryFactoryPreview"), request);
    }

    /**
     * '발주' 입하이력 미리보기 상세 아이템 목록 조회
     * @param request 그룹핑 조회 조건
     * @return 전표 상세 아이템 목록
     */
    public List<InstockHistoryResponse.DetailItem> selectInstockHistoryFactoryDetailItems(InstockHistoryRequest.FactoryDetail request) {
        return sqlSession.selectList(NAMESPACE.concat(".selectInstockHistoryFactoryDetailItems"), request);
    }

    /**
     *  '매장분반납' 입하이력 미리보기 상세 조회
     */
    public InstockHistoryResponse.PrintDetail selectInstockHistoryReturnPreview(InstockHistoryRequest.ReturnDetail request) {
        return sqlSession.selectOne(NAMESPACE.concat(".selectInstockHistoryReturnPreview"), request);
    }

    /**
     *  '매장분반납' 입하이력 미리보기 상세 아이템 목록 조회
     *  @param request 그룹핑 조회 조건
     *  @return 전표 상세 아이템 목록
     */
    public List<InstockHistoryResponse.DetailItem> selectInstockHistoryReturnDetailItems(InstockHistoryRequest.ReturnDetail request) {
        return sqlSession.selectList(NAMESPACE.concat(".selectInstockHistoryReturnDetailItems"), request);
    }

    /**
     * '발주' 입하이력 상세 내역 조회
     */
    public List<InstockHistoryResponse.InstockHistoryFactoryDetail> selectInstockHistoryFactoryDetail(InstockHistoryRequest.FactoryDetail request) {
        return sqlSession.selectList(NAMESPACE.concat(".selectInstockHistoryFactoryDetail"), request);
    }

    /**
     * '매장분 반납' 입하이력 상세 내역 조회
     */
    public List<InstockHistoryResponse.InstockHistoryReturnDetail> selectInstockHistoryReturnDetail(InstockHistoryRequest.ReturnDetail request) {
        return sqlSession.selectList(NAMESPACE.concat(".selectInstockHistoryReturnDetail"), request);
    }

    /**
     * 발주 입하여부 조회
     */
    public Integer selectInstockCount(InstockHistoryRequest.CancelInstock request) {
        return sqlSession.selectOne(NAMESPACE.concat(".selectInstockCount"), request);
    }

    /**
     * 입고삭제
     */
    public Integer deleteStock(InstockHistoryRequest.CancelInstock request) {
        return sqlSession.update(NAMESPACE.concat(".deleteStock"), request);
    }
}