package com.shop.core.biz.system.dao;

import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.entity.UserCode;
import com.shop.core.biz.system.vo.request.UserCodeRequest;
import com.shop.core.biz.system.vo.response.UserCodeResponse;
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
public class UserCodeDao {

    private final String PRE_NS = "com.shop.mapper.UserCode.";

    @Qualifier("sqlSessionTemplate")
    private final SqlSession sqlSession;

    /**
     * 코드관리_목록_조회 (페이징)
     *
     * @param pageRequest
     * @return
     */
    public PageResponse<UserCodeResponse.Paging> selectUserCodeListPaging(PageRequest<UserCodeRequest.PagingFilter> pageRequest) {
        List<UserCodeResponse.Paging> codes = sqlSession.selectList(PRE_NS.concat("selectUserCodeListPaging"), pageRequest);
        if (codes != null && !codes.isEmpty()) {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount(), codes, codes.get(0).getTotalRowCount());
        } else {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount());
        }
    }

    /**
     * 코드_콤보_조회 (by code)
     *
     * @param UserCodeRequest
     * @return
     */
    public List<UserCodeResponse.UserCodeDropDown> selectLowerCodeByUserCodeUpper(UserCodeRequest.UserCodeDropDown UserCodeRequest) {
        return sqlSession.selectList(PRE_NS.concat("selectLowerCodeByUserCodeUpper"), UserCodeRequest);
    }


    /**
     * 코드_조회 (by uk)
     *
     * @param userId
     * @param codeUpper
     * @param codeCd
     * @return
     */
    public UserCode selectUserCodeByUk(Integer userId, String codeUpper, String codeCd) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("codeUpper", codeUpper);
        params.put("codeCd", codeCd);
        return sqlSession.selectOne(PRE_NS.concat("selectUserCodeByUk"), params);
    }


    /**
     * 코드_조회 (by uk)
     *
     * @param userId
     * @param codeUpper
     * @param column
     * @return
     */
    public String getDupCodeInfo(Integer userId, String codeUpper, String column) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("codeUpper", codeUpper);
        params.put("column", column);
        return sqlSession.selectOne(PRE_NS.concat("getDupCodeInfo"), params);
    }


    /**
     * 하위_코드_조회 (by codeUpper) 코드 관리 화면에서
     *
     * @param userCodeRequest
     * @return
     */
    public List<UserCodeResponse.LowerSelect> selectLowerCodeByCodeUpperForUserCodeMng(UserCodeRequest.UserCodeDropDown userCodeRequest) {
        return sqlSession.selectList(PRE_NS.concat("selectLowerCodeByCodeUpperForUserCodeMng"), userCodeRequest);
    }

    /**
     * 하위_코드_조회 (by codeUpper) 주로 fo 에서
     *
     * @param userCodeRequest
     * @return
     */
    public List<UserCodeResponse.LowerSelect> selectUserCodeList(UserCodeRequest.UserCodeDropDown userCodeRequest) {
        return sqlSession.selectList(PRE_NS.concat("selectUserCodeList"), userCodeRequest);
    }


    /**
     * 코드_등록
     *
     * @param code
     * @return
     */
    public void insertUserCode(UserCode code) {
        sqlSession.insert(PRE_NS.concat("insertUserCode"), code);
    }

    /**
     * 코드_수정
     *
     * @param userCode
     * @return
     */
    public void updateUserCode(UserCode userCode) {
        sqlSession.update(PRE_NS.concat("updateUserCode"), userCode);
    }


    /**
     * 코드_수정_존재한것만
     *
     * @param userCode
     * @return
     */
    public void updateUserCodeExistOnly(UserCode userCode) {
        sqlSession.update(PRE_NS.concat("updateUserCodeExistOnly"), userCode);
    }




    /**
     * 코드_삭제
     *
     * @param code
     * @return
     */
    public Integer deleteUserCode(UserCode code) {
        return sqlSession.delete(PRE_NS.concat("deleteUserCode"), code);
    }


    /**
     * 코드_값 조회 ( 자동생성되게하기 위해 )
     *
     * @param userId
     * @param codeUpper
     * @return
     */
    public Integer getAutoGenCodeCd(Integer userId, String codeUpper) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("codeUpper", codeUpper);
        return sqlSession.selectOne(PRE_NS.concat("getAutoGenCodeCd"), params);
    }
}
