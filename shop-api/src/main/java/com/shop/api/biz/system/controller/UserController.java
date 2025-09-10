package com.shop.api.biz.system.controller;

import com.shop.api.annotation.AccessLog;
import com.shop.api.annotation.JwtUser;
import com.shop.api.biz.system.service.UserService;
import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.biz.system.vo.request.UserRequest;
import com.shop.core.biz.system.vo.response.ApiResponse;
import com.shop.core.biz.system.vo.response.UserResponse;
import com.shop.core.biz.system.vo.response.UserResponse.SelectByLoginId;
import com.shop.core.entity.User;
import com.shop.core.enums.*;
import com.shop.core.exception.CustomRuntimeException;
import com.shop.core.utils.CommUtil;
import com.shop.core.utils.CryptUtil;
import com.shop.core.utils.MailUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <pre>
 * Description: 계정 Controller
 * Date: 27/01/2023 1:01 AM
 * Company: smart90
 * Author : luckeey
 * </pre>
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(name = "UserController", description = "계정 관련 API")
public class UserController {

    private final UserService userService;

    private final MailUtil mailUtil;

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

        // 권한 399이하 일때
        if(jwtUser.getAuthCd() != null && Integer.parseInt(jwtUser.getAuthCd()) <= 399){
            // partnerId 조회
            User userResponse = userService.selectUserById(jwtUser.getId());
            // upperPartnerId가 userResponse의 id인 리스트
            filter.setPartnerId(userResponse.getPartnerId());
        }

        pageRequest.setFilter(filter);

        // 계정관리_목록_조회 (페이징)
        PageResponse<UserResponse.Paging> response = userService.selectUserPaging(pageRequest);

        return new ApiResponse<>(ApiResultCode.SUCCESS, response);
    }

    /**
     * 계정 목록조회 (designer 조회)
     * @param jwtUser
     * @return
     */
    /*@GetMapping(value = "/designer")
    @Operation(summary = "계정 목록조회 (designer 조회)")
    public ApiResponse<List<User>> selectDesinerUserList(
            @Parameter(hidden = true) @JwtUser User jwtUser
    ) {
        // 로그인 한 유저 partnerId 구하기
        User loginUser = userService.selectUserById(jwtUser.getId());
        if (loginUser == null) {
            throw new CustomRuntimeException(ApiResultCode.FAIL);
        }
        Integer partnerId = loginUser.getPartnerId();

        // 계정_조회
        List<User> userResponse = userService.selectDesinerUserList(partnerId);

        if (userResponse == null) {
            return new ApiResponse<>(ApiResultCode.NOT_FOUND_USER);
        }

        return new ApiResponse<>(ApiResultCode.SUCCESS, userResponse);
    }*/
    @GetMapping(value = "/designer")
    @Operation(summary = "계정 목록조회 (350 조회)")
    public ApiResponse<List<User>> selectDesinerUserList350(
            @Parameter(hidden = true) @JwtUser User jwtUser
    ) {
        // 로그인 한 유저 partnerId 구하기
        User loginUser = userService.selectUserById(jwtUser.getId());
        if (loginUser == null) {
            throw new CustomRuntimeException(ApiResultCode.FAIL);
        }
        Integer partnerId = loginUser.getPartnerId();
        // 350 조건으로 조회
        List<User> userResponse350 = userService.selectDesinerUserListByAuth350(partnerId);
        if (userResponse350 == null || userResponse350.isEmpty()) {
            return new ApiResponse<>(ApiResultCode.NOT_FOUND_USER);
        }
        return new ApiResponse<>(ApiResultCode.SUCCESS, userResponse350);
    }

    @GetMapping(value = "/mulyu")
    @Operation(summary = "계정 목록조회 (입하작업자 조회)")
    public ApiResponse<List<User>> selectInstockUserList(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @RequestParam(required = false) Integer workLogisId
    ) {
        // 500번대 조건으로 조회
        List<User> userResponse500 = userService.selectInstockUserList(workLogisId);
        if (userResponse500 == null || userResponse500.isEmpty()) {
            return new ApiResponse<>(ApiResultCode.NOT_FOUND_USER);
        }
        return new ApiResponse<>(ApiResultCode.SUCCESS, userResponse500);
    }

    /**
     * 계정 목록조회 (designer 조회 - 399)
     * @param partnerId
     * @return
     */
    @GetMapping(value = "/partner/{partnerId}")
    @Operation(summary = "계정 목록조회 (399 화주이하 조회)")
    public ApiResponse<List<User>> selectDesinerUserList399(
            @PathVariable Integer partnerId  // partnerId를 경로 파라미터로 받음
    ) {
        // 399 조건으로 조회
        List<User> userResponse399 = userService.selectDesinerUserListByAuth399(partnerId);
        if (userResponse399 == null || userResponse399.isEmpty()) {
            return new ApiResponse<>(ApiResultCode.NOT_FOUND_USER);  // 계정 목록이 없을 경우
        }
        return new ApiResponse<>(ApiResultCode.SUCCESS, userResponse399);  // 결과 반환
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
                || StringUtils.isEmpty(userRequest.getPhoneNo()) || StringUtils.isEmpty(userRequest.getAuthCd())) {
            return new ApiResponse<>(ApiResultCode.NO_REQUIRED_VALUE);
        }
        if(userRequest.getPartnerId() == 0){ // 계정 파트너 설정 안했을 시 null로 설정
            userRequest.setPartnerId(null);
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

        //String chgPass = CommUtil.getRamdomPassword();
        String chgPass = userRequest.getPhoneNo();

        // 세션정보를 이용해서 등록자 세팅
        userRequest.setCreUser(jwtUser.getLoginId());

        // 비밀번호 암호화
        try {
            String encPass = CryptUtil.getHash(chgPass, userRequest.getLoginId().getBytes());
            userRequest.setLoginPass(encPass);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ApiResponse<>(ApiResultCode.ENC_ERROR);
        }

        if (StringUtils.isNotEmpty(userRequest.getAuthCd()) && userRequest.getAuthCd().length() == 3) {
            userRequest.setUserType(userRequest.getAuthCd().substring(0, 1));
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
        userRequest.setUpdUser(jwtUser.getLoginId());

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
        userRequest.setUpdUser(jwtUser.getLoginId());

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
                //mailCntn = mailUtil.makeEmailCntt(MailType.CREATE_ID, userRequest.getLoginId(), userRequest.getLoginPass(), user.getLanguageCode());
                //mailUtil.sendMail(GlobalConst.MAIL_TITLE_REG, userRequest.getLoginId(), GlobalConst.MAIL_SENDER, mailCntn, user.getLanguageCode());
            } else if (MailType.DEL_ID.equals(userRequest.getMailType())) {
                //mailCntn = mailUtil.makeEmailCntt(MailType.DEL_ID, userRequest.getLoginId(), "", user.getLanguageCode());
                //mailUtil.sendMail(GlobalConst.MAIL_TITLE_DEL, userRequest.getLoginId(), GlobalConst.MAIL_SENDER, mailCntn, user.getLanguageCode());
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
                userRequest.setUpdUser(jwtUser.getLoginId());
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

        // 유저 핸드폰번호로 임시비밀번호 세팅
        User user = userService.selectUserById(userRequest.getId());
        String chgPass = user.getPhoneNo();

        // String chgPass = CommUtil.getRamdomPassword();

        Integer rsltCnt = 1;
        //commonService.sendSmsForPass(SmsType.PASS_INIT, phone, chgPass, userRequest.getId(), user.getLanguageCode());

        if (rsltCnt > 0) {
            try {
                // 메시지 전송되면 user 업데이트
                // 비밀번호 암호화
                String encPassword = CryptUtil.getHash(chgPass, userRequest.getLoginId().getBytes());

                // 세션정보를 이용해서 수정자 세팅
                userRequest.setUpdUser(jwtUser.getLoginId());
                userRequest.setLoginPass(encPassword);

                // 계정_비밀번호초기화
                Integer updateCount = userService.updatePasswordInit(userRequest);

                if (updateCount == 0) {
                    throw new CustomRuntimeException(ApiResultCode.FAIL_DELETE);
                }
                log.debug("djqepdl {}", updateCount);

            } catch (Exception e) {
                throw new CustomRuntimeException(ApiResultCode.FAIL_PASS_INIT);
            }
            return new ApiResponse<>(ApiResultCode.SUCCESS);
        } else {
            throw new CustomRuntimeException(ApiResultCode.FAIL_SEND_SMS);
        }
    }


    /**
     * 계정_권한 추가
     *
     * @param jwtUser
     * @param userId
     * @return
     */
    @AccessLog("화주권한 모두 만들기")
    @GetMapping("/createAuthForPartner/{userId}")
    @Operation(summary = "계정 수정")
    public ApiResponse createAuthForPartner(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(description = "화주권한 모두 만들기") @PathVariable Integer userId  // partnerId를 경로 파라미터로 받음
    ) {
        userService.createAuthForPartner(userId, jwtUser.getLoginId());
        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }

}
