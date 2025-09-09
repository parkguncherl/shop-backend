package com.shop.core.wms.ipgo.dao;

import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.entity.Asn;
import com.shop.core.wms.ipgo.vo.request.AsnRequest;
import com.shop.core.wms.ipgo.vo.response.AsnResponse;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AsnDao {

    private final SqlSession sqlSession;
    private static final String NAMESPACE = "com.binblur.mapper.wms.ipgo.AsnMapper";

    /**
     * WMS 발주목록 페이징
     */
    public PageResponse<AsnResponse.Paging> selectAsnListPaging(PageRequest<AsnRequest.PagingFilter> pageRequest) {
        List<AsnResponse.Paging> asns = sqlSession.selectList(NAMESPACE.concat(".selectAsnListPaging"), pageRequest);
        if (asns != null && !asns.isEmpty()) {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount(), asns, asns.get(0).getTotalRowCount());
        } else {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount());
        }
    }

    /**
     * WMS 발주목록 통계 대시보드
     */
    public AsnResponse.StatDashBoard selectAsnStatForDashBoard(AsnRequest.StatDashBoard request) {
        return sqlSession.selectOne(NAMESPACE.concat(".selectAsnStatForDashBoard"), request);
    }

    /**
     * WMS 발주 상세 목록
     */
    public List<AsnResponse.Detail> selectAnsDtlList(AsnRequest.Detail request) {
        return sqlSession.selectList(NAMESPACE.concat(".selectAnsDtlList"), request);
    }

    /**
     * WMS 발주 전표 상세
     */
    public AsnResponse.PrintDetail selectAsnPrintDetail(AsnRequest.Detail request) {
        return sqlSession.selectOne(NAMESPACE.concat(".selectAsnPrintDetail"), request);
    }

    /**
     * WMS 발주 전표 상세
     */
    public List<AsnResponse.DetailItem> selectAsnPrintDetailItems(AsnRequest.Detail request) {
        return sqlSession.selectList(NAMESPACE.concat(".selectAsnPrintDetailItems"), request);
    }

    public Asn selectAsn(Integer id) {
        return sqlSession.selectOne(NAMESPACE.concat(".selectAsn"), id);
    }

    /**
     * WMS 발주 전표 상세
     */
    public Integer updateAsn(AsnRequest.UpdateAsn request) {
        return sqlSession.update(NAMESPACE.concat(".updateAsn"), request);
    }



}