package com.shop.core.biz.partnerVendor.dao;

import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.biz.partnerVendor.vo.request.PartnerVendorRequest;
import com.shop.core.biz.partnerVendor.vo.response.PartnerVendorResponse;
import com.shop.core.entity.PartnerVendor;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PartnerVendorDao {

    private final SqlSession sqlSession;
    private static final String NAMESPACE = "com.shop.mapper.PartnerVendor.";

    /**
     * 협력업체 목록(페이징)을 조회합니다.
     */
    public PageResponse<PartnerVendorResponse.Paging> selectPartnerVendorListPaging(PageRequest<PartnerVendorRequest.PagingFilter> pageRequest) {
        List<PartnerVendorResponse.Paging> vendors = sqlSession.selectList(NAMESPACE.concat("selectPartnerVendorListPaging"), pageRequest);
        int totalCount = vendors.isEmpty() ? 0 : vendors.get(0).getTotalRowCount();
        return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount(), vendors, totalCount);
    }

    /**
     * 협력업체 단건을 조회합니다.
     */
    public PartnerVendor selectPartnerVendorById(Integer id) {
        return sqlSession.selectOne(NAMESPACE.concat("selectPartnerVendorById"), id);
    }

    /**
     * 새로운 협력업체를 생성합니다.
     */
    public int insertPartnerVendor(PartnerVendorRequest.Create request) {
        return sqlSession.insert(NAMESPACE.concat("insertPartnerVendor"), request);
    }

    /**
     * 기존 협력업체를 수정합니다.
     */
    public Integer updatePartnerVendorExistOnly(PartnerVendorRequest.Update request) {
        return sqlSession.update(NAMESPACE.concat("updatePartnerVendorExistOnly"), request);
    }

    /**
     * 협력업체를 논리적으로 삭제합니다.
     */
    public Integer deletePartnerVendor(PartnerVendorRequest.Delete request) {
        return sqlSession.update(NAMESPACE.concat("deletePartnerVendor"), request);
    }
}
