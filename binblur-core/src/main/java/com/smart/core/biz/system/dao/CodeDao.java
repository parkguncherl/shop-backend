package com.binblur.core.biz.system.dao;

import com.binblur.core.biz.common.vo.request.PageRequest;
import com.binblur.core.biz.common.vo.response.PageResponse;
import com.binblur.core.biz.system.vo.request.CodeRequest;
import com.binblur.core.biz.system.vo.response.CodeResponse;
import com.binblur.core.entity.Code;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <pre>
 * Description : 코드_관리 Dao
 * Date : 2023/02/06 11:58 AM
 * Company : smart90
 * Author : sclee9946
 * </pre>
 */
@Repository
public class CodeDao {

    private final String PRE_NS = "com.binblur.mapper.code.";

    @Autowired
    @Qualifier("sqlSessionTemplate")
    private SqlSession sqlSession;

    /**
     * 코드관리_목록_조회 (페이징)
     *
     * @param pageRequest
     * @return
     */
    public PageResponse<CodeResponse.Paging> selectCodePaging(PageRequest<CodeRequest.PagingFilter> pageRequest) {
        List<CodeResponse.Paging> codes = sqlSession.selectList(PRE_NS.concat("selectCodePaging"), pageRequest);
        if (codes != null && !codes.isEmpty()) {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount(), codes, codes.get(0).getTotalRowCount());
        } else {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount());
        }
    }

    /**
     * 코드 목록 조회
     *
     * @return
     */
    public List<CodeResponse.Select> selectCodeList() {
        return sqlSession.selectList(PRE_NS.concat("selectCodeList"));
    }

    /**
     * 코드_콤보_조회 (by code)
     *
     * @param codeRequest
     * @return
     */
    public List<CodeResponse.CodeDropDown> selectDropdownByCodeUpper(CodeRequest.CodeDropDown codeRequest) {
        return sqlSession.selectList(PRE_NS.concat("selectDropdownByCodeUpper"), codeRequest);
    }

    /**
     * 코드_조회 (by Id)
     *
     * @param codeId
     * @return
     */
    public CodeResponse.Select selectCodeById(Integer codeId) {
        return sqlSession.selectOne(PRE_NS.concat("selectCodeById"), codeId);
    }

    /**
     * 코드_조회 (by Uk)
     *
     * @param code
     * @return
     */
    public Code selectCodeByUk(Code code) {
        return sqlSession.selectOne(PRE_NS.concat("selectCodeByUk"), code);
    }

    /**
     * 코드_조회 (by Uk) 삭제된(미사용)것 모두 조회가능
     *
     * @param code
     * @return
     */
    public Code selectCodeByUkIncludeDelete(Code code) {
        return sqlSession.selectOne(PRE_NS.concat("selectCodeByUkIncludeDelete"), code);
    }

    /**
     * 하위_코드_조회 (by codeUpper)
     *
     * @param codeUpper
     * @return
     */
    public List<CodeResponse.LowerSelect> selectLowerCodeByCodeUpper(String codeUpper) {
        List<CodeResponse.LowerSelect> list = sqlSession.selectList(PRE_NS.concat("selectLowerCodeByCodeUpper"), codeUpper);
        return list;
    }



    /**
     * 하위_코드_조회 (by codeUpper) 코드 관리 화면에서
     *
     * @param codeUpper
     * @return
     */
    public List<CodeResponse.LowerSelect> selectLowerCodeByCodeUpperForCodeMng(String codeUpper) {
        return sqlSession.selectList(PRE_NS.concat("selectLowerCodeByCodeUpperForCodeMng"), codeUpper);
    }

    

    /**
     * 코드_등록
     *
     * @param code
     * @return
     */
    public Integer insertCode(Code code) {
        return sqlSession.insert(PRE_NS.concat("insertCode"), code);
    }

    /**
     * 코드_수정
     *
     * @param code
     * @return
     */
    public Integer updateCode(Code code) {
        return sqlSession.update(PRE_NS.concat("updateCode"), code);
    }

    /**
     * 코드_수정
     *
     * @param code
     * @return
     */
    public Integer updateCodeByUk(Code code) {
        return sqlSession.update(PRE_NS.concat("updateCodeByUk"), code);
    }

    /**
     * 코드_삭제
     *
     * @param code
     * @return
     */
    public Integer deleteCode(Code code) {
        return sqlSession.delete(PRE_NS.concat("deleteCode"), code);
    }

    /**
     * 코드_삭제 (by codeUpper)
     *
     * @param codeUpper
     * @return
     */
    public Integer deleteCodesByCodeUpper(String codeUpper) {
        return sqlSession.delete(PRE_NS.concat("deleteCodesByCodeUpper"), codeUpper);
    }

    /**
     * 코드_삭제 상위코드만
     *
     * @param codeCd
     * @return
     */
    public Integer deleteCodeRealUk(String codeCd) {
        return sqlSession.delete(PRE_NS.concat("deleteCodeRealUk"), codeCd);
    }


    
    
    /**
     * 코드_엑셀_일괄_다운로드_목록_조회
     * @param columnIds
     * @return
     */
    /*public List<CodeResponse.Excel> selectCodeExcelList(String columnIds) {
        return sqlSession.selectList(PRE_NS.concat("selectCodeExcelList"), columnIds);
    }*/

    /**
     * 코드_엑셀_다운로드_조회
     *
     * @return
     */
/*    public List<CodeResponse.Excel> selectCodeExcel(CodeRequest.PagingFilter filter) {
        return sqlSession.selectList(PRE_NS.concat("selectCodeExcel"), filter);
    }*/

    /**
     * 코드_삭제 (전체)
     *
     * @return
     */
    public Integer deleteCodeAll() {
        return sqlSession.delete(PRE_NS.concat("deleteCodeAll"), null);
    }

    /**
     * 충전프로파일_차트_변수_조회 (by codeUpper)
     *
     * @param codeUpper
     * @return
     */
    public List<CodeResponse.Chart> selectParamByCodeUpper(String codeUpper) {
        return sqlSession.selectList(PRE_NS.concat("selectParamByCodeUpper"), codeUpper);
    }

    /**
     * 코드관리_목록_조회 (속성 연관)
     *
     * @param pageRequest
     * @return
     */
    public PageResponse<CodeResponse.Paging> selectRelatedCodePaging(PageRequest<CodeRequest.PagingFilter> pageRequest) {
        List<CodeResponse.Paging> codes = sqlSession.selectList(PRE_NS.concat("selectRelatedCodeList"), pageRequest);
        if (codes != null && !codes.isEmpty()) {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount(), codes, codes.size());
        } else {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount());
        }
    }


}
