package com.shop.api.biz.system.service;

import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.biz.system.dao.HistoryDao;
import com.shop.core.entity.PartnerCode;
import com.shop.core.entity.User;
import com.shop.core.enums.ApiResultCode;
import com.shop.core.exception.CustomRuntimeException;
import com.shop.core.biz.system.dao.PartnerCodeDao;
import com.shop.core.biz.system.vo.request.PartnerCodeRequest;
import com.shop.core.biz.system.vo.response.PartnerCodeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
public class PartnerCodeService {

    private final PartnerCodeDao partnerCodeDao;
    private final UserService userService;
    private final HistoryDao historyDao;


    /**
     * 코드관리_목록_조회 (페이징)
     *
     * @param pageRequest
     * @return
     */
    public PageResponse<PartnerCodeResponse.Paging> selectCodePaging(PageRequest<PartnerCodeRequest.PagingFilter> pageRequest) {
        return partnerCodeDao.selectPartnerCodeListPaging(pageRequest);
    }
    /**
     * 코드_콤보_조회 (by CodeUpper)
     *
     * @param partnerCodeRequest
     * @return
     */
    public List<PartnerCodeResponse.PartnerCodeDropDown> selectLowerCodeByPartnerCodeUpper(PartnerCodeRequest.PartnerCodeDropDown partnerCodeRequest) {
        return partnerCodeDao.selectLowerCodeByPartnerCodeUpper(partnerCodeRequest);
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
        return partnerCodeDao.selectPartnerCodeByUk(partnerId, codeUpper, codeCd);
    }


    /**
     * 하위_코드_조회 (by codeUpper) 코드화면에서 만 사용
     *
     * @param partnerCodeRequest
     * @return
     */
    public List<PartnerCodeResponse.LowerSelect> selectLowerCodeByCodeUpperForPartnerCodeMng(PartnerCodeRequest.PartnerCodeDropDown partnerCodeRequest) {
        return partnerCodeDao.selectLowerCodeByCodeUpperForPartnerCodeMng(partnerCodeRequest);
    }


    /**
     * 코드_등록
     *
     * @param codeRequest
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void savePartnerCodes(PartnerCodeRequest.Create codeRequest, User jwtUser ) {
        User user = userService.selectUserById(jwtUser.getId());
        ArrayList<PartnerCodeResponse.LowerSelect> newCodeList = codeRequest.getPartnerCodeLowerSelectList();

        Integer partnerId = user.getPartnerId();
        String partnerCodeUpper = "";

        // 필수값 체크
        if (newCodeList == null || newCodeList.isEmpty()) {
            throw new CustomRuntimeException(ApiResultCode.FAIL_CREATE, "코드정보가 없습니다.");
        }

        // validation
        for(PartnerCode partnerCode : newCodeList) {
            if(StringUtils.isEmpty(partnerCodeUpper)){
                partnerCodeUpper = partnerCode.getCodeUpper();
            }

           if(StringUtils.isEmpty(partnerCode.getCodeCd())){
                    throw new CustomRuntimeException(ApiResultCode.FAIL, "코드값이 존재하지 않습니다.");
                }
            if(StringUtils.isEmpty(partnerCode.getCodeNm())){
                throw new CustomRuntimeException(ApiResultCode.FAIL, "코드명이 존재하지 않습니다.");
            }

        }

        for(PartnerCodeResponse.LowerSelect lowerSelect : newCodeList) {
            lowerSelect.setPartnerId(user.getPartnerId());
            lowerSelect.setCreUser(jwtUser.getLoginId());
            lowerSelect.setUpdUser(jwtUser.getLoginId());

            // 신규
            if (lowerSelect.getId() == null || lowerSelect.getId() == 0) {
                this.insertPartnerCode(lowerSelect.toEntity());
                // 수정
            } else {
                this.updatePartnerCode(lowerSelect.toEntity());
            }
        }

        String result = partnerCodeDao.getDupCodeInfo(partnerId, partnerCodeUpper, "CODE_CD");

        if (StringUtils.isNotEmpty(result)) {
            throw new CustomRuntimeException(ApiResultCode.FAIL, result + "코드(CODE_CD)가 중복되어 있습니다.");
        }

        result = partnerCodeDao.getDupCodeInfo(partnerId, partnerCodeUpper, "CODE_NM");

        if (StringUtils.isNotEmpty(result)) {
            throw new CustomRuntimeException(ApiResultCode.FAIL, result + "코드명(CODE_NM)가 중복되어 있습니다.");
        }
    }

    /**
     * 코드_등록
     *
     * @param partnerCode
     * @return
     */
    public void insertPartnerCode(PartnerCode partnerCode) {
        partnerCodeDao.insertPartnerCode(partnerCode);
    }

    /**
     * 코드_수정
     *
     * @param partnerCode
     * @return
     */
    public void updatePartnerCode(PartnerCode partnerCode) {
        partnerCodeDao.updatePartnerCode(partnerCode);
    }


    /**
     * 코드_삭제
     *
     * @param partnerCodeRequest
     * @return
     */
    public Integer deleteCode(PartnerCodeRequest.Delete partnerCodeRequest) {
        return partnerCodeDao.deletePartnerCode(partnerCodeRequest.toEntity());
    }

    /**
     * 코드_소프트삭제
     *
     * @param partnerCodeRequest
     * @param jwtUser
     * @return
     */
    public Boolean updatePartnerCodeToDeletedStatus(PartnerCodeRequest.SoftDelete partnerCodeRequest,  User jwtUser) {
        User user = userService.selectUserById(jwtUser.getId());
        PartnerCode partnerCode = new PartnerCode();
        partnerCode.setPartnerId(user.getPartnerId());
        partnerCode.setUpdUser(user.getLoginId());
        if (partnerCodeDao.deletePartnerCode(partnerCode) == 0) {
            throw new CustomRuntimeException(ApiResultCode.FAIL_DELETE);
        }
        return true;
    }

}
