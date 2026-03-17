package com.shop.core.biz.system.dao;

import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.entity.PartnerCode;
import com.shop.core.biz.system.vo.request.PartnerCodeRequest;
import com.shop.core.biz.system.vo.response.PartnerCodeResponse;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * Description: 코드_관리 Dao
 * Date: 2023/02/06 11:58 AM
 * Company: smart90
 * Author: luckeey
 * </pre>
 */
@Repository
@RequiredArgsConstructor
public class PartnerCodeDao {

    private final String PRE_NS = "com.shop.mapper.partnerCode.";

    @Qualifier("sqlSessionTemplate")
    private final SqlSession sqlSession;

    /**
     * 코드관리_목록_조회 (페이징)
     *
     * @param pageRequest
     * @return
     */
    public PageResponse<PartnerCodeResponse.Paging> selectPartnerCodeListPaging(PageRequest<PartnerCodeRequest.PagingFilter> pageRequest) {
        List<PartnerCodeResponse.Paging> codes = sqlSession.selectList(PRE_NS.concat("selectPartnerCodeListPaging"), pageRequest);
        if (codes != null && !codes.isEmpty()) {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount(), codes, codes.get(0).getTotalRowCount());
        } else {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount());
        }
    }

    /**
     * 코드_콤보_조회 (by code)
     *
     * @param PartnerCodeRequest
     * @return
     */
    public List<PartnerCodeResponse.PartnerCodeDropDown> selectLowerCodeByPartnerCodeUpper(PartnerCodeRequest.PartnerCodeDropDown PartnerCodeRequest) {
        return sqlSession.selectList(PRE_NS.concat("selectLowerCodeByPartnerCodeUpper"), PartnerCodeRequest);
    }


    /**
     * 코드_조회 (by uk)
     *
     * @param partnerId
     * @param codeUpper
     * @param codeCd
     * @return
     */
    public PartnerCode selectPartnerCodeByUk(Integer partnerId, String codeUpper, String codeCd) {
        Map<String, Object> params = new HashMap<>();
        params.put("partnerId", partnerId);
        params.put("codeUpper", codeUpper);
        params.put("codeCd", codeCd);
        return sqlSession.selectOne(PRE_NS.concat("selectPartnerCodeByUk"), params);
    }


    /**
     * 코드_조회 (by uk)
     *
     * @param partnerId
     * @param codeUpper
     * @param column
     * @return
     */
    public String getDupCodeInfo(Integer partnerId, String codeUpper, String column) {
        Map<String, Object> params = new HashMap<>();
        params.put("partnerId", partnerId);
        params.put("codeUpper", codeUpper);
        params.put("column", column);
        return sqlSession.selectOne(PRE_NS.concat("getDupCodeInfo"), params);
    }


    /**
     * 하위_코드_조회 (by codeUpper) 코드 관리 화면에서
     *
     * @param partnerCodeRequest
     * @return
     */
    public List<PartnerCodeResponse.LowerSelect> selectLowerCodeByCodeUpperForPartnerCodeMng(PartnerCodeRequest.PartnerCodeDropDown partnerCodeRequest) {
        return sqlSession.selectList(PRE_NS.concat("selectLowerCodeByCodeUpperForPartnerCodeMng"), partnerCodeRequest);
    }

    /**
     * 코드_등록
     *
     * @param code
     * @return
     */
    public void insertPartnerCode(PartnerCode code) {
        sqlSession.insert(PRE_NS.concat("insertPartnerCode"), code);
    }

    /**
     * 코드_수정
     *
     * @param partnerCode
     * @return
     */
    public void updatePartnerCode(PartnerCode partnerCode) {
        sqlSession.update(PRE_NS.concat("updatePartnerCode"), partnerCode);
    }

    /**
     * 코드_삭제
     *
     * @param code
     * @return
     */
    public Integer deletePartnerCode(PartnerCode code) {
        return sqlSession.delete(PRE_NS.concat("deletePartnerCode"), code);
    }

}
