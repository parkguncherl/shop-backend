package com.shop.api.biz.system.service;

import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.entity.UserCode;
import com.shop.core.entity.User;
import com.shop.core.enums.ApiResultCode;
import com.shop.core.exception.CustomRuntimeException;
import com.shop.core.biz.system.dao.UserCodeDao;
import com.shop.core.biz.system.vo.request.UserCodeRequest;
import com.shop.core.biz.system.vo.response.UserCodeResponse;
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
public class UserCodeService {

    private final UserCodeDao userCodeDao;
    private final UserService userService;


    /**
     * 코드관리_목록_조회 (페이징)
     *
     * @param pageRequest
     * @return
     */
    public PageResponse<UserCodeResponse.Paging> selectCodePaging(PageRequest<UserCodeRequest.PagingFilter> pageRequest) {
        return userCodeDao.selectUserCodeListPaging(pageRequest);
    }
    /**
     * 코드_콤보_조회 (by CodeUpper)
     *
     * @param userCodeRequest
     * @return
     */
    public List<UserCodeResponse.UserCodeDropDown> selectLowerCodeByUserCodeUpper(UserCodeRequest.UserCodeDropDown userCodeRequest) {
        return userCodeDao.selectLowerCodeByUserCodeUpper(userCodeRequest);
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
        return userCodeDao.selectUserCodeByUk(userId, codeUpper, codeCd);
    }


    /**
     * 하위_코드_조회 (by codeUpper) 코드화면에서 만 사용
     *
     * @param userCodeRequest
     * @return
     */
    public List<UserCodeResponse.LowerSelect> selectLowerCodeByCodeUpperForUserCodeMng(UserCodeRequest.UserCodeDropDown userCodeRequest) {
        return userCodeDao.selectLowerCodeByCodeUpperForUserCodeMng(userCodeRequest);
    }



    /**
     * 하위_코드_조회 (by codeUpper) 주로 fo에서
     *
     * @param userCodeRequest
     * @return
     */
    public List<UserCodeResponse.LowerSelect> selectUserCodeList(UserCodeRequest.UserCodeDropDown userCodeRequest) {
        return userCodeDao.selectUserCodeList(userCodeRequest);
    }

    /**
     * 코드_등록
     *
     * @param codeRequest
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveUserCodes(UserCodeRequest.Create codeRequest, User jwtUser ) {
        User user = userService.selectUserById(jwtUser.getId());
        ArrayList<UserCodeResponse.LowerSelect> newCodeList = codeRequest.getUserCodeLowerSelectList();

        Integer userId = user.getId();
        String userCodeUpper = "";

        // 필수값 체크
        if (newCodeList == null || newCodeList.isEmpty()) {
            throw new CustomRuntimeException(ApiResultCode.FAIL_CREATE, "코드정보가 없습니다.");
        }

        // validation
        for(UserCode userCode : newCodeList) {
            if(StringUtils.isEmpty(userCodeUpper)){
                userCodeUpper = userCode.getCodeUpper();
            }


            if("NO_CODECD_AUTO_INCREMENT".equals(codeRequest.getCreateType())) {
                Integer codeVal = this.getAutoGenCodeCd(user.getId(), userCodeUpper);
                userCode.setCodeCd(codeVal.toString());
            }

           if(StringUtils.isEmpty(userCode.getCodeCd())){
                    throw new CustomRuntimeException(ApiResultCode.FAIL, "코드값이 존재하지 않습니다.");
                }
            if(StringUtils.isEmpty(userCode.getCodeNm())){
                throw new CustomRuntimeException(ApiResultCode.FAIL, "코드명이 존재하지 않습니다.");
            }

        }

        for(UserCodeResponse.LowerSelect lowerSelect : newCodeList) {
            lowerSelect.setUserId(user.getId());
            lowerSelect.setCreUser(jwtUser.getLoginId());
            lowerSelect.setUpdUser(jwtUser.getLoginId());

            // 신규
            if (lowerSelect.getId() == null || lowerSelect.getId() == 0) {
                this.insertUserCode(lowerSelect.toEntity());
                // 수정
            } else {
                this.updateUserCode(lowerSelect.toEntity());
            }
        }

        String result = userCodeDao.getDupCodeInfo(userId, userCodeUpper, "CODE_CD");

        if (StringUtils.isNotEmpty(result)) {
            throw new CustomRuntimeException(ApiResultCode.FAIL, result + "코드(CODE_CD)가 중복되어 있습니다.");
        }

        result = userCodeDao.getDupCodeInfo(userId, userCodeUpper, "CODE_NM");

        if (StringUtils.isNotEmpty(result)) {
            throw new CustomRuntimeException(ApiResultCode.FAIL, result + "코드명(CODE_NM)가 중복되어 있습니다.");
        }
    }

    /**
     * 코드_등록
     *
     * @param userCode
     * @return
     */
    public void insertUserCode(UserCode userCode) {
        userCodeDao.insertUserCode(userCode);
    }

    /**
     * 코드_수정
     *
     * @param userCode
     * @return
     */
    public void updateUserCode(UserCode userCode) {
        userCodeDao.updateUserCode(userCode);
    }

    /**
     * 코드정보 변경 단건 (없으면 등록, 있으면 수정)
     *
     * @param codeRequest 코드 변경 Request
     * @param jwtUser     로그인 사용자
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveUserCodeVal(UserCodeRequest.UpdateUserCodeVal codeRequest, User jwtUser) {
        // 필수값 체크
        if (StringUtils.isEmpty(codeRequest.getCodeUpper())) {
            throw new CustomRuntimeException(ApiResultCode.NO_REQUIRED_VALUE, "파트너상위코드가 입력되지 않았습니다.");
        }

        User user = userService.selectUserById(jwtUser.getId());
        if (user.getId() == null || user.getId() == 0) {
            throw new CustomRuntimeException(ApiResultCode.NO_REQUIRED_VALUE, "로그인정보에 파트너 정보가 없습니다.");
        }

        codeRequest.setUserId(user.getId());

        // 코드_조회 (by Uk)
        UserCode userCode = this.selectUserCodeByUk(codeRequest.getUserId(), codeRequest.getCodeUpper(), codeRequest.getCodeCd());

        if (userCode == null) {
            // 없으면 등록
            userCode = new UserCode();
            userCode.setUserId(codeRequest.getUserId());
            userCode.setCodeUpper(codeRequest.getCodeUpper());
            userCode.setCodeCd(codeRequest.getCodeCd());
            userCode.setCodeNm(codeRequest.getCodeNm());
            userCode.setCodeOrder(1); // 일단 1번으로 등록
            userCode.setCreUser(jwtUser.getLoginId());
            userCode.setUpdUser(jwtUser.getLoginId());
            this.insertUserCode(userCode);
        } else {
            // 있으면 수정
            userCode.setCodeNm(codeRequest.getCodeNm());
            userCode.setUpdUser(jwtUser.getLoginId());
            this.updateUserCode(userCode);
        }
    }


    /**
     * 코드_삭제
     *
     * @param userCodeRequest
     * @return
     */
    public Integer deleteCode(UserCodeRequest.Delete userCodeRequest) {
        return userCodeDao.deleteUserCode(userCodeRequest.toEntity());
    }

    /**
     * 코드_소프트삭제
     *
     * @param userCodeRequest
     * @param jwtUser
     * @return
     */
    public Boolean updateUserCodeToDeletedStatus(UserCodeRequest.SoftDelete userCodeRequest,  User jwtUser) {
        User user = userService.selectUserById(jwtUser.getId());
        UserCode userCode = new UserCode();
        userCode.setUserId(user.getId());
        userCode.setUpdUser(user.getLoginId());
        if (userCodeDao.deleteUserCode(userCode) == 0) {
            throw new CustomRuntimeException(ApiResultCode.FAIL_DELETE);
        }
        return true;
    }

    /**
     * 코드_값 조회 ( 자동생성되게하기 위해 )
     *
     * @param userId
     * @param codeUpper
     * @return
     */
    public Integer getAutoGenCodeCd(Integer userId, String codeUpper) {
        return userCodeDao.getAutoGenCodeCd(userId, codeUpper);
    }
}
