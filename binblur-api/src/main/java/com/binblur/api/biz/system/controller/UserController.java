package com.binblur.api.biz.system.controller;

import com.binblur.api.annotation.AccessLog;
import com.binblur.api.annotation.JwtUser;
import com.binblur.api.biz.system.service.UserService;
import com.binblur.core.biz.common.vo.request.PageRequest;
import com.binblur.core.biz.common.vo.response.PageResponse;
import com.binblur.core.biz.system.vo.request.UserRequest;
import com.binblur.core.biz.system.vo.response.ApiResponse;
import com.binblur.core.biz.system.vo.response.UserResponse;
import com.binblur.core.biz.system.vo.response.UserResponse.SelectByLoginId;
import com.binblur.core.entity.User;
import com.binblur.core.enums.*;
import com.binblur.core.utils.CommUtil;
import com.binblur.core.utils.CryptUtil;
import com.binblur.core.utils.MailUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <pre>
 * Description : 계정 Controller
 * Date : 27/01/2023 1:01 AM
 * Company : smart90
 * Author : harry
 * </pre>
 */
@Slf4j
@RestController
@RequestMapping("/user")
@Tag(name = "UserController", description = "계정 관련 API")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailUtil mailUtil;

    /**
     * 계정관리 목록 조회 (페이징)
     *
     * @param jwtUser
     * @param filter
     * @param pageRequest
     * @return
     */
    @AccessLog("계정관리 목록 조회")
    @GetMapping(value = "/paging")
    @Operation(summary = "계정관리 목록 조회 (페이징)")
    public ApiResponse<PageResponse<UserResponse.Paging>> selectUserPaging(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(name = "UserRequestPagingFilter", description = "계정관리 목록 조회 (페이징) 필터", in = ParameterIn.PATH) UserRequest.PagingFilter filter,
            @Parameter(name = "PageRequest", description = "계정관리 목록 조회 페이징") PageRequest<UserRequest.PagingFilter> pageRequest
    ) {
        if (StringUtils.isEmpty(filter.getAuthCd())) {
            filter.setMyAuthCd(jwtUser.getAuthCd());
        }
        pageRequest.setFilter(filter);
        // 계정관리_목록_조회 (페이징)
        PageResponse<UserResponse.Paging> response = userService.selectUserPaging(pageRequest);

        return new ApiResponse<>(ApiResultCode.SUCCESS, response);
    }

    /**
     * 계정관리_조회 (by LoginId)
     *
     * @param loginId
     * @return
     */
    @GetMapping(value = "/{loginId}")
    @Operation(summary = "계정관리 조회")
    public ApiResponse<UserResponse.SelectByLoginId> selectUserByLoginId(
            @Parameter(description = "계정 아이디") @PathVariable String loginId
    ) {
        // 필수값 체크
        if (StringUtils.isEmpty(loginId)) {
            return new ApiResponse<>(ApiResultCode.NO_REQUIRED_VALUE);
        }

        // 계정관리_조회 (by LoginId)
        UserResponse.SelectByLoginId userResponse = userService.selectUserByLoginId(loginId);

        if (userResponse == null) {
            return new ApiResponse<>(ApiResultCode.NOT_FOUND_USER);
        }

        return new ApiResponse<>(userResponse);
    }

    /**
     * 계정_등록
     *
     * @param jwtUser
     * @param userRequest
     * @return
     */
    @AccessLog("계정 등록")
    @PostMapping()
    @Operation(summary = "계정 등록")
    public ApiResponse<UserResponse.Select> insertUser(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(description = "계정 등록 Request") @RequestBody UserRequest.Create userRequest
    ) {
        // 필수값 체크
        if (StringUtils.isEmpty(userRequest.getLoginId()) || StringUtils.isEmpty(userRequest.getUserNm())
                || StringUtils.isEmpty(userRequest.getLanguageCode()) || StringUtils.isEmpty(userRequest.getPhoneNo())
                || StringUtils.isEmpty(userRequest.getAuthCd()) || StringUtils.isEmpty(userRequest.getBelongNm())) {
            return new ApiResponse<>(ApiResultCode.NO_REQUIRED_VALUE);
        }

        // 본인보다 권한이 크면 생성할수 없다.
        if (StringUtils.isNotEmpty(userRequest.getAuthCd())
                && Integer.parseInt(userRequest.getAuthCd()) > Integer.parseInt(userService.selectUserById(jwtUser.getId()).getAuthCd())) {
            return new ApiResponse<>(ApiResultCode.UPPER_AUTH_INVALIE);
        }

        CommUtil.mailFieldCheck(EsseType.ESS, userRequest.getLoginId(), 100, "아이디");

        CommUtil.phoneFieldCheck(EsseType.ESS, userRequest.getPhoneNo(), 11, "전화번호");

        User user = new User();
        user.setLoginId(userRequest.getLoginId());

        // 계정_조회 (by Uk)
        User existUser = userService.selectUserByUk(user);

        // 중복 계정 체크
        if (existUser != null) {
            return new ApiResponse<>(ApiResultCode.DUPLICATE_ID);
        }

        String chgPass = CommUtil.getRamdomPassword();

        // 세션정보를 이용해서 등록자 세팅
        userRequest.setCreateUser(jwtUser.getLoginId());

        // 비밀번호 암호화
        try {
            String encPass = CryptUtil.getHash(chgPass, userRequest.getLoginId().getBytes());
            userRequest.setLoginPass(encPass);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ApiResponse<>(ApiResultCode.ENC_ERROR);
        }

        // 계정_등록
        Integer insertCount = userService.insertUser(userRequest);

        if (insertCount == 0) {
            return new ApiResponse<>(ApiResultCode.FAIL_CREATE);
        }

        UserResponse.Select createdUser = new UserResponse.Select();
        createdUser.setLoginId(userRequest.getLoginId());
        createdUser.setLoginPass(chgPass);

        return new ApiResponse<>(ApiResultCode.SUCCESS, createdUser);
    }

    /**
     * 계정_수정
     *
     * @param jwtUser
     * @param userRequest
     * @return
     */
    @AccessLog("계정 수정")
    @PutMapping()
    @Operation(summary = "계정 수정")
    public ApiResponse updateUser(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(description = "계정 수정 Request") @RequestBody UserRequest.Update userRequest
    ) {
        // 필수값 체크
        if (StringUtils.isEmpty(userRequest.getId().toString())) {
            return new ApiResponse<>(ApiResultCode.NO_REQUIRED_VALUE);
        }

        if(userRequest.getPhoneNo().indexOf("*") > -1){
            userRequest.setPhoneNo(null);
        } else {
            CommUtil.phoneFieldCheck(EsseType.ESS, userRequest.getPhoneNo(), 11, "전화번호");
        }

        User user = userService.selectUserById(jwtUser.getId());
        // 본인보다 권한이 크면 생성할수 없다.
        if (StringUtils.isNotEmpty(userRequest.getAuthCd())
                && Integer.parseInt(userRequest.getAuthCd()) > Integer.parseInt(user.getAuthCd())) {
            return new ApiResponse<>(ApiResultCode.UPPER_AUTH_INVALIE);
        }

        // 세션정보를 이용해서 수정자 세팅
        userRequest.setUpdateUser(jwtUser.getLoginId());

        // 비밀번호 암호화
        try {
            if (!StringUtils.isEmpty(userRequest.getLoginPass())) {
                String encPass = CryptUtil.getHash(userRequest.getLoginPass(), userRequest.getLoginId().getBytes());
                userRequest.setLoginPass(encPass);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ApiResponse<>(ApiResultCode.ENC_ERROR);
        }

        // 계정_수정
        Integer updateCount = userService.updateUser(userRequest);

        if (updateCount == 0) {
            return new ApiResponse<>(ApiResultCode.FAIL_UPDATE);
        }

        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }

    /**
     * 계정_삭제
     *
     * @param jwtUser
     * @param userRequest
     * @return
     */
    @AccessLog("계정 삭제")
    @DeleteMapping()
    @Operation(summary = "계정 삭제")
    public ApiResponse deleteUser(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(description = "계정 삭제 Request") @RequestBody UserRequest.Delete userRequest
    ) {
        // 필수값 체크
        if (StringUtils.isEmpty(userRequest.getId().toString())) {
            return new ApiResponse<>(ApiResultCode.NO_REQUIRED_VALUE);
        }

        // 본인 계정 삭제 불가
        if (jwtUser.getLoginId().equals(userRequest.getLoginId())) {
            return new ApiResponse<>(ApiResultCode.FAIL_DELETE_ME);
        }

        // 세션정보를 이용해서 수정자 세팅
        userRequest.setUpdateUser(jwtUser.getLoginId());

        // 계정_삭제
        Integer deleteCount = userService.deleteUser(userRequest);

        if (deleteCount == 0) {
            return new ApiResponse<>(ApiResultCode.FAIL_DELETE);
        }

        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }

    /**
     * 계정_메일_보내기
     *
     * @param jwtUser
     * @param userRequest
     * @return
     */
    @AccessLog("계정 메일 보내기")
    @PostMapping(value = "/send-mail")
    @Operation(summary = "계정 메일 보내기")
    public ApiResponse sendMailUser(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(description = "계정 메일 보내기 Request") @RequestBody UserRequest.Email userRequest
    ) {
        // 필수값 체크
        if (StringUtils.isEmpty(userRequest.getLoginId()) || StringUtils.isEmpty(userRequest.getMailType().toString())) {
            return new ApiResponse<>(ApiResultCode.NO_REQUIRED_VALUE);
        }

        UserResponse.SelectByLoginId user = new SelectByLoginId();

        if (MailType.DEL_ID.equals(userRequest.getMailType())) {
            // 삭제된_최신_계정관리_조회 (by LoginId)
            user = userService.selectDelUserByLoginId(userRequest.getLoginId());
        } else {
            // 계정관리_조회 (by LoginId)
            user = userService.selectUserByLoginId(userRequest.getLoginId());

            if (user == null) {
                return new ApiResponse<>(ApiResultCode.USER_NOT_FOUND);
            }
        }

        String mailCntn = "";

        try {
            if (MailType.CREATE_ID.equals(userRequest.getMailType())) {
                mailCntn = mailUtil.makeEmailCntt(MailType.CREATE_ID, userRequest.getLoginId(), userRequest.getLoginPass(), user.getLanguageCode());
                mailUtil.sendMail(GlobalConst.MAIL_TITLE_REG, userRequest.getLoginId(), GlobalConst.MAIL_SENDER, mailCntn, user.getLanguageCode());
            } else if (MailType.DEL_ID.equals(userRequest.getMailType())) {
                mailCntn = mailUtil.makeEmailCntt(MailType.DEL_ID, userRequest.getLoginId(), "", user.getLanguageCode());
                mailUtil.sendMail(GlobalConst.MAIL_TITLE_DEL, userRequest.getLoginId(), GlobalConst.MAIL_SENDER, mailCntn, user.getLanguageCode());
            }
            // 메일 보내기

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ApiResponse<>(ApiResultCode.EMAIL_ERROR);
        }

        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }

    /**
     * 계정_잠금해제
     *
     * @param jwtUser
     * @param userRequest
     * @return
     */
    @AccessLog("계정 잠금해제")
    @PutMapping(value = "/un-lock")
    @Operation(summary = "계정 잠금해제")
    public ApiResponse updateUserUnLock(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(description = "계정 잠금해제 Request") @RequestBody UserRequest.UnLock userRequest
    ) {
        // 필수값 체크
        if (StringUtils.isEmpty(userRequest.getId().toString()) || StringUtils.isEmpty(userRequest.getLoginPass())) {
            return new ApiResponse<>(ApiResultCode.NO_REQUIRED_VALUE);
        }

        // 계정관리_조회 (by LoginId)
        UserResponse.SelectByLoginId userResponse = userService.selectUserByLoginId(jwtUser.getLoginId());

        try {
            String adminEncPassword = CryptUtil.getHash(userRequest.getLoginPass(), jwtUser.getLoginId().getBytes());

            if (!userResponse.getLoginPass().equals(adminEncPassword)) {
                return new ApiResponse<>(ApiResultCode.NOT_MATCHED_NOW_PASS);
            }
        } catch (Exception e) {
            return new ApiResponse<>(ApiResultCode.ENC_ERROR);
        }

        String chgPass = CommUtil.getRamdomPassword();

        String phone = userRequest.getPhoneNo();
        Integer rsltCnt = 1; //commonService.sendSmsForPass(SmsType.UN_LOCK, phone, chgPass, userRequest.getId(), userResponse.getLanguageCode());

        if (rsltCnt > 0) {
            try {
                // 메시지 전송되면 user 업데이트
                String encPassword = CryptUtil.getHash(chgPass, userRequest.getLoginId().getBytes());

                // 세션정보를 이용해서 수정자 세팅
                userRequest.setUpdateUser(jwtUser.getLoginId());
                userRequest.setLoginPass(encPassword);

                // 계정_잠금해제
                userService.updateUserUnLock(userRequest);

            } catch (Exception e) {
                return new ApiResponse<>(ApiResultCode.FAIL_UNLOCK);
            }
            return new ApiResponse<>(ApiResultCode.SUCCESS);
        } else {
            return new ApiResponse<>(ApiResultCode.FAIL_SEND_SMS);
        }
    }

    /**
     * 계정_비밀번호초기화
     *
     * @param jwtUser
     * @param userRequest
     * @return
     */
    @AccessLog("계정 비밀번호 초기화")
    @PutMapping(value = "/password-init")
    @Operation(summary = "계정 비밀번호 초기화")
    public ApiResponse updatePasswordInit(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(description = "계정 비밀번호초기화 Request") @RequestBody UserRequest.PasswordInit userRequest
    ) {
        // 필수값 체크
        if (StringUtils.isEmpty(userRequest.getId().toString())) {
            return new ApiResponse<>(ApiResultCode.NO_REQUIRED_VALUE);
        }

        String chgPass = CommUtil.getRamdomPassword();

        User user = userService.selectUserById(userRequest.getId());
        String phone = user.getPhoneNo();
        Integer rsltCnt = 1;
        //commonService.sendSmsForPass(SmsType.PASS_INIT, phone, chgPass, userRequest.getId(), user.getLanguageCode());

        if (rsltCnt > 0) {
            try {
                // 메시지 전송되면 user 업데이트
                String encPassword = CryptUtil.getHash(chgPass, userRequest.getLoginId().getBytes());

                // 세션정보를 이용해서 수정자 세팅
                userRequest.setUpdateUser(jwtUser.getLoginId());
                userRequest.setLoginPass(encPassword);

                // 계정_비밀번호초기화
                userService.updatePasswordInit(userRequest);

            } catch (Exception e) {
                return new ApiResponse<>(ApiResultCode.FAIL_PASS_INIT);
            }
            return new ApiResponse<>(ApiResultCode.SUCCESS);
        } else {
            return new ApiResponse<>(ApiResultCode.FAIL_SEND_SMS);
        }
    }

}
