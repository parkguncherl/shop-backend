package com.shop.core.wms.fee.dao;

import com.shop.core.entity.PartnerFee;
import com.shop.core.wms.fee.vo.request.FeeRequest;
import com.shop.core.wms.fee.vo.response.FeeResponse;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 적치정보 DAO
 */

@Repository
@RequiredArgsConstructor
public class FeeDao {

    private final SqlSession sqlSession;
    private static final String NAMESPACE = "com.shop.mapper.wms.fee.feeMapper";

    /**
     * 적치 목록을 페이징하여 조회
     * @param partnerFeeRequestForList 필터값
     * @return 적치목록
     */
    public List<FeeResponse.PartnerFeeResponse> partnerFeeList(FeeRequest.PartnerFeeRequestForList partnerFeeRequestForList) {
        return sqlSession.selectList(NAMESPACE.concat(".partnerFeeList"), partnerFeeRequestForList);
    }



    /**
     * 적치 목록을 페이징하여 조회
     * @param partnerId
     * @return 적치목록
     */
    public PartnerFee selectPartnerFeeByPartnerId(Integer partnerId) {
        return sqlSession.selectOne(NAMESPACE.concat(".selectPartnerFeeByPartnerId"), partnerId);
    }

    /**
     * 적치 목록을 페이징하여 조회
     * @param partnerFee
     * @return 적치목록
     */
    public void createPartnerFee(PartnerFee partnerFee) {
        sqlSession.insert(NAMESPACE.concat(".insertPartnerFee"), partnerFee);
    }

    /**
     * 적치 목록을 페이징하여 조회
     * @param partnerFee
     * @return 적치목록
     */
    public void deletePartnerFee(PartnerFee partnerFee) {
        sqlSession.update(NAMESPACE.concat(".deletePartnerFee"), partnerFee);
    }
}