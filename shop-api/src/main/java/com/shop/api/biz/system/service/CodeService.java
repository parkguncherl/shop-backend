package com.shop.api.biz.system.service;

import com.shop.api.properties.GlobalProperties;
import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.biz.system.dao.CodeDao;
import com.shop.core.biz.system.vo.request.CodeRequest;
import com.shop.core.biz.system.vo.response.CodeResponse;
import com.shop.core.entity.Code;
import com.shop.core.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <pre>
 * Description: 코드_관리 Service
 * Date: 2023/02/06 11:57 AM
 * Company: smart90
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CodeService {

    private final CodeDao codeDao;

    private final GlobalProperties globalProperties;

    /**
     * 코드관리_목록_조회 (페이징)
     *
     * @param pageRequest
     * @return
     */
    public PageResponse<CodeResponse.Paging> selectCodePaging(PageRequest<CodeRequest.PagingFilter> pageRequest) {
        return codeDao.selectCodePaging(pageRequest);
    }

    /**
     * 코드_목록_조회
     *
     * @return
     */
    public List<CodeResponse.Select> selectCodeList() {
        return codeDao.selectCodeList();
    }

    /**
     * 코드_콤보_조회 (by CodeUpper)
     *
     * @param codeRequest
     * @return
     */
    public List<CodeResponse.CodeDropDown> selectDropdownByCodeUpper(CodeRequest.CodeDropDown codeRequest) {
        return codeDao.selectDropdownByCodeUpper(codeRequest);
    }

    /**
     * 코드_조회 (by Id)
     *
     * @param codeId
     * @return
     */
    public CodeResponse.Select selectCodeById(Integer codeId) {
        return codeDao.selectCodeById(codeId);
    }

    /**
     * 코드_조회 (by Uk)
     *
     * @param code
     * @return
     */
    public Code selectCodeByUk(Code code) {
        return codeDao.selectCodeByUk(code);
    }


    /**
     * 코드_조회 (by Uk) 삭제된것도 모두 조회
     *
     * @param code
     * @return
     */
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
    public List<CodeResponse.LowerSelect> selectLowerCodeByCodeUpperForCodeMng(String codeUpper) {
        return codeDao.selectLowerCodeByCodeUpperForCodeMng(codeUpper);
    }



    /**
     * 코드_등록
     *
     * @param codeRequest
     * @return
     */
    public Integer insertCode(CodeRequest.Create codeRequest) {
        return codeDao.insertCode(codeRequest.toEntity());
    }

    /**
     * 코드_수정
     *
     * @param codeRequest
     * @return
     */
    public Integer updateCode(CodeRequest.Update codeRequest) {
        return codeDao.updateCode(codeRequest.toEntity());
    }


    /**
     * 코드_수정
     *
     * @param code
     * @return
     */
    public Integer updateCodeByUk(Code code) {
        return codeDao.updateCodeByUk(code);
    }



    /**
     * 코드_수정
     *
     * @param codeRequest
     * @return
     */
    public Integer updateCode(CodeRequest.Create codeRequest) {
        return codeDao.updateCode(codeRequest.toEntity());
    }

    /**
     * 코드_삭제
     *
     * @param codeRequest
     * @return
     */
    public Integer deleteCode(CodeRequest.Delete codeRequest) {
        return codeDao.deleteCode(codeRequest.toEntity());
    }


    /**
     * 코드_삭제 (복수)
     *
     * @param codeRequestList
     */
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
    public void insertCodes(String codeUpper, List<CodeRequest.Create> codeRequestList, User jwtUser) {
        // 코드_삭제 (by codeUpper)
        codeDao.deleteCodesByCodeUpper(codeUpper);
        for (int i = 0; i < codeRequestList.size(); i++) {

            CodeRequest.Create codeRequest = codeRequestList.get(i);
            codeRequest.setCodeOrder(i + 1);

            // 세션정보를 이용해서 등록자 세팅
            codeRequest.setCreUser(jwtUser.getLoginId());

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
    public String selectCodeDesc(String codeUpper, String codeCd) {
        Code code = new Code();
        code.setCodeUpper(codeUpper);
        code.setCodeCd(codeCd);
        String descCntn = codeDao.selectCodeByUk(code).getCodeDesc();
        return descCntn == null ? "" : descCntn;
    }


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
            code.setCreUser(jwtUser.getLoginId());
            codeDao.insertCode(code.toEntity());
        }
    }


    /**
     * 코드_하위모두삭제
     *
     * @param codeUpper
     * @return
     */
    public Integer deleteCodesByCodeUpper(String codeUpper) {
        return codeDao.deleteCodesByCodeUpper(codeUpper);
    }



    /**
     * 코드_대분류삭제
     *
     * @param codeCd
     * @return
     */
    public Integer deleteCodeRealUk(String codeCd) {
        return codeDao.deleteCodeRealUk(codeCd);
    }


    /**
     * 코드관리_목록_조회 (속성 연관)
     *
     * @param pageRequest
     * @return
     */
    public PageResponse<CodeResponse.Paging> selectRelatedCodePaging(PageRequest<CodeRequest.PagingFilter> pageRequest) {
        return codeDao.selectRelatedCodePaging(pageRequest);
    }

    /**
     * 상품분류 코드조회
     *
     * @param codeUpper
     * @return
     */
    public List<CodeResponse.CodeDropDown> getFuncCdListByCodeUpper(String codeUpper) {
        return codeDao.getFuncCdListByCodeUpper(codeUpper);
    }


    /**
     * 코드관리_목록_조회 (속성 연관)
     *
     * @param codeUk
     * @return
     */
    public String selectCodeName(CodeRequest.CodeUk codeUk) {
        return codeDao.selectCodeName(codeUk);
    }

}
