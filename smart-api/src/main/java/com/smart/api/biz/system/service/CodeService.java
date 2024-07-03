package com.smart.api.biz.system.service;

import com.smart.api.properties.GlobalProperties;
import com.smart.core.biz.common.vo.request.PageRequest;
import com.smart.core.biz.common.vo.response.PageResponse;
import com.smart.core.biz.system.dao.CodeDao;
import com.smart.core.biz.system.vo.request.CodeRequest;
import com.smart.core.biz.system.vo.response.CodeResponse;
import com.smart.core.entity.Code;
import com.smart.core.entity.User;
import com.smart.core.utils.CommUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <pre>
 * Description : 코드_관리 Service
 * Date : 2023/02/06 11:57 AM
 * Company : smart90
 * </pre>
 */
@Slf4j
@Service
@Transactional(transactionManager = "dataTxManager")
public class CodeService {

    @Autowired
    private CodeDao codeDao;

    @Autowired
    private GlobalProperties globalProperties;

    /**
     * 코드관리_목록_조회 (페이징)
     *
     * @param pageRequest
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public PageResponse<CodeResponse.Paging> selectCodePaging(PageRequest<CodeRequest.PagingFilter> pageRequest) {
        return codeDao.selectCodePaging(pageRequest);
    }

    /**
     * 코드_목록_조회
     *
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<CodeResponse.Select> selectCodeList() {
        return codeDao.selectCodeList();
    }

    /**
     * 코드_콤보_조회 (by CodeUpper)
     *
     * @param codeRequest
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<CodeResponse.CodeDropDown> selectDropdownByCodeUpper(CodeRequest.CodeDropDown codeRequest) {
        return codeDao.selectDropdownByCodeUpper(codeRequest);
    }

    /**
     * 코드_조회 (by Id)
     *
     * @param codeId
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public CodeResponse.Select selectCodeById(Integer codeId) {
        return codeDao.selectCodeById(codeId);
    }

    /**
     * 코드_조회 (by Uk)
     *
     * @param code
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Code selectCodeByUk(Code code) {
        return codeDao.selectCodeByUk(code);
    }


    /**
     * 코드_조회 (by Uk) 삭제된것도 모두 조회
     *
     * @param code
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Code selectCodeByUkIncludeDelete(Code code) {
        return codeDao.selectCodeByUkIncludeDelete(code);
    }




    /**
     * 하위_코드_조회 (by codeUpper)
     *
     * @param codeUpper
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<CodeResponse.LowerSelect> selectLowerCodeByCodeUpper(String codeUpper) {
        return codeDao.selectLowerCodeByCodeUpper(codeUpper);
    }

    /**
     * 하위_코드_조회 (by codeUpper) 코드화면에서 만 사용
     *
     * @param codeUpper
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<CodeResponse.LowerSelect> selectLowerCodeByCodeUpperForCodeMng(String codeUpper) {
        return codeDao.selectLowerCodeByCodeUpperForCodeMng(codeUpper);
    }



    /**
     * 코드_등록
     *
     * @param codeRequest
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer insertCode(CodeRequest.Create codeRequest) {
        return codeDao.insertCode(codeRequest.toEntity());
    }

    /**
     * 코드_수정
     *
     * @param codeRequest
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer updateCode(CodeRequest.Update codeRequest) {
        return codeDao.updateCode(codeRequest.toEntity());
    }


    /**
     * 코드_수정
     *
     * @param code
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer updateCodeByUk(Code code) {
        return codeDao.updateCodeByUk(code);
    }



    /**
     * 코드_수정
     *
     * @param codeRequest
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer updateCode(CodeRequest.Create codeRequest) {
        return codeDao.updateCode(codeRequest.toEntity());
    }

    /**
     * 코드_삭제
     *
     * @param codeRequest
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer deleteCode(CodeRequest.Delete codeRequest) {
        return codeDao.deleteCode(codeRequest.toEntity());
    }


    /**
     * 코드_삭제 (복수)
     *
     * @param codeRequestList
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteCodes(List<CodeRequest.Delete> codeRequestList) {
        codeRequestList.forEach(item -> {
            // 코드_삭제
            codeDao.deleteCode(item.toEntity());
        });
    }

    /**
     * 하위_코드_저장 (복수)
     *
     * @param codeRequestList
     * @param jwtUser
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insertCodes(String codeUpper, List<CodeRequest.Create> codeRequestList, User jwtUser) {
        // 코드_삭제 (by codeUpper)
        codeDao.deleteCodesByCodeUpper(codeUpper);
        for (int i = 0; i < codeRequestList.size(); i++) {

            CodeRequest.Create codeRequest = codeRequestList.get(i);
            codeRequest.setCodeOrder(i + 1);

            // 세션정보를 이용해서 등록자 세팅
            codeRequest.setCreateUser(jwtUser.getLoginId());

            // 코드_등록
            codeDao.insertCode(codeRequest.toEntity());
        }
    }

    /**
     * 코드_명 조회(by Uk)
     *
     * @param codeUpper
     * @param codeCd
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public String selectCodeNm(String codeUpper, String codeCd) {
        Code code = new Code();
        code.setCodeUpper(codeUpper);
        code.setCodeCd(codeCd);
        return codeDao.selectCodeByUk(code).getCodeNm();
    }

    /**
     * 코드_etcInfo 값 조회(by Uk)
     *
     * @param codeUpper
     * @param codeCd
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public String selectCodeEtc1(String codeUpper, String codeCd, String lan) {
        Code code = new Code();
        code.setCodeUpper(codeUpper);
        code.setCodeCd(codeCd);
        if (!StringUtils.equals("KR", lan)) {
            return codeDao.selectCodeByUk(code).getCodeEtc2();
        } else {
            return codeDao.selectCodeByUk(code).getCodeEtc1();
        }
    }

    /**
     * 코드_설명_조회(by Uk)
     *
     * @param codeUpper
     * @param codeCd
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public String selectCodeDesc(String codeUpper, String codeCd) {
        Code code = new Code();
        code.setCodeUpper(codeUpper);
        code.setCodeCd(codeCd);
        String descCntn = codeDao.selectCodeByUk(code).getCodeDesc();
        return descCntn == null ? "" : descCntn;
    }

    /**
     * 코드_엑셀_일괄_다운로드_목록_조회
     * @param columnIds
     * @return
     */
    /*public List<CodeResponse.Excel> selectCodeExcelList(String columnIds) {
        return codeDao.selectCodeExcelList(columnIds);
    }*/

    /**
     * 코드_엑셀_다운로드_조회
     *
     * @return
     */
/*    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<CodeResponse.Excel> selectCodeExcel(CodeRequest.PagingFilter filter) {
        return codeDao.selectCodeExcel(filter);
    }*/

    /**
     * 코드_엑셀_업로드
     *
     * @param codeRequestList
     * @param jwtUser
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void insertCodes(List<CodeRequest.Create> codeRequestList, User jwtUser) {
        for (CodeRequest.Create code : codeRequestList) {
            code.setCreateUser(jwtUser.getLoginId());
            codeDao.insertCode(code.toEntity());
        }
    }


    /**
     * 코드_하위모두삭제
     *
     * @param codeUpper
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer deleteCodesByCodeUpper(String codeUpper) {
        return codeDao.deleteCodesByCodeUpper(codeUpper);
    }



    /**
     * 코드_대분류삭제
     *
     * @param codeCd
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer deleteCodeRealUk(String codeCd) {
        return codeDao.deleteCodeRealUk(codeCd);
    }


    /**
     * 코드관리_목록_조회 (속성 연관)
     *
     * @param pageRequest
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public PageResponse<CodeResponse.Paging> selectRelatedCodePaging(PageRequest<CodeRequest.PagingFilter> pageRequest) {
        return codeDao.selectRelatedCodePaging(pageRequest);
    }


}
