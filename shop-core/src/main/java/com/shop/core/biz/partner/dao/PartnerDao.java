package com.shop.core.biz.partner.dao;

import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.biz.partner.vo.request.PartnerRequest;
import com.shop.core.biz.partner.vo.response.PartnerResponse;
import com.shop.core.entity.Partner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PartnerDao {

    private final String PRE_NS = "com.shop.mappers.partner.";

    @Qualifier("sqlSessionTemplate")
    private final SqlSession sqlSession;

    /**
     * 화주관리_목록_조회 (리스트)
     *
     * @return
     */
    public List<PartnerResponse.Select> selectPartnerList(Integer logisId) {
        return sqlSession.selectList(PRE_NS.concat("selectPartnerList"), logisId);
    }

    /**
     * 내파트너만 조회하기
     */
    public PartnerResponse.Select selectMyPartnerByLoginId(String loginId) {
        return sqlSession.selectOne("selectMyPartnerByLoginId", loginId);
    }
/**
     * 화주관리_목록_조회 (페이징)
     *
     * @param pageRequest
     * @return
     */
    public PageResponse<PartnerResponse.Paging> selectPartnerPaging(PageRequest<PartnerRequest.PagingFilter> pageRequest) {
        List<PartnerResponse.Paging> partners = sqlSession.selectList(PRE_NS.concat("selectPartnerListPaging"), pageRequest);

        int totalRowCount = 0; // 빈 리스트일 때의 기본 totalRowCount 값

        // 리스트가 비어 있지 않을 때만 totalRowCount를 가져옴
        if (partners != null && !partners.isEmpty()) {
            totalRowCount = partners.get(0).getTotalRowCount();
        }

        return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount(), partners, totalRowCount);
    }

    /**
     * 화주관리_목록_조회 (검색)
     *
     * @return
     */
    public List<PartnerResponse.ForSearching> selectPartnerListForSearching(PartnerRequest.FilterForList filterForList) {
        return sqlSession.selectList(PRE_NS.concat("selectPartnerListForSearching"), filterForList);
    }

    /**
     * 화주_등록
     *
     * @param partner
     * @return
     */
    public Integer insertPartner(Partner partner) {
        return sqlSession.insert(PRE_NS.concat("insertPartner"), partner);
    }

    /**
     * 화주_조회 (by Id)
     *
     * @param partnerId
     * @return
     */
    public Partner selectPartnerById(Integer partnerId) {
        return sqlSession.selectOne(PRE_NS.concat("selectPartnerById"), partnerId);
    }


    /**
     * getPartnerId 조회
     *
     * @return
     */
    public Integer getPartnerId(String partnerNm) {
        return sqlSession.selectOne(PRE_NS.concat("getPartnerId"), partnerNm);
    }


    /**
     * 화주_수정
     *
     * @param partner
     * @return
     */
    public Integer updatePartner(Partner partner) {
        return sqlSession.update(PRE_NS.concat("updatePartner"), partner);
    }


    /**
     * 화주_삭제
     *
     * @param partner
     * @return
     */
    public Integer deletePartner(Partner partner) {
        return sqlSession.update(PRE_NS.concat("deletePartner"), partner);
    }
}
