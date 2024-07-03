package com.smart.api.biz.system.controller;

import com.smart.api.annotation.AccessLog;
import com.smart.api.annotation.JwtUser;
import com.smart.api.biz.system.service.*;
import com.smart.core.annotations.NotAuthRequired;
import com.smart.core.biz.system.vo.request.LoginRequest;
import com.smart.core.biz.system.vo.request.UserRequest;
import com.smart.core.biz.system.vo.response.ApiResponse;
import com.smart.core.biz.system.vo.response.AuthResponse;
import com.smart.core.biz.system.vo.response.LoginResponse;
import com.smart.core.biz.system.vo.response.UserResponse;
import com.smart.core.entity.AuthToken;
import com.smart.core.entity.JwtAuthToken;
import com.smart.core.entity.User;
import com.smart.core.enums.*;
import com.smart.core.utils.CommUtil;
import com.smart.core.utils.CryptUtil;
import com.smart.core.utils.MailUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.CookieGenerator;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * <pre>
 * Description : 인증 Controller
 * Date : 2023/01/26 12:35 PM
 * Company : smart90
 * Author : harry
 * </pre>
 */
@Slf4j
@RestController
@RequestMapping("/auth")
@Tag(name = "AuthController", description = "인증 관련 API")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthTokenService authTokenService;

    @Autowired
    private UserService userService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private MailUtil mailUtil;

    @Autowired
    private MenuService menuService;

    @Value("${cors.endpoint.url}")
    private String corsUrls;

    @Value("${free.pass.user.id}")
    private String freePassUserId;

    @NotAuthRequired
    @PostMapping(value = "/verification")
    @Operation(summary = "계정 확인")
    public ApiResponse<LoginResponse> verification(@RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
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
        updateUser.setLoginLanguage(loginRequest.getCountryCode());
        updateUser.setLastLoginDateTime(LocalDateTime.now());
        updateUser.setLoginFailCnt(0);
        updateUser.setOtpNo(loginRequest.getOtpNo());
        updateUser.setOtpFailCnt(0);
        userService.updateUser(updateUser);
//        userResponse.setLoginLanguage(loginRequest.getCountryCode());
//        userResponse.setLanguageCode(loginRequest.getCountryCode());

        // 쿠키생성
        CookieGenerator cg = new CookieGenerator();
        cg.setCookieName(GlobalConst.COUNTRY_COOKIE.getCode());
        cg.addCookie(response, loginRequest.getCountryCode());

        // locale 설정
        CommUtil.setLocale(request, response, loginRequest.getCountryCode());

        //--------------------------------------------------------------------------------
        // 최종 결과값 엔티티 생성
        //--------------------------------------------------------------------------------
        loginResponse.setUser(userResponse);
        loginResponse.setToken(token);

        /* 공통 로깅 서비스*/
        contactService.logging(userResponse, "로그인", null);
        return new ApiResponse<>(ApiResultCode.SUCCESS, loginResponse);
    }

    @NotAuthRequired
    @GetMapping(value = "/logout/{loginId}")
    @Operation(summary = "로그아웃")
    @AccessLog("로그아웃")
    public ApiResponse logout(@Parameter(description = "계정 아이디") @PathVariable String loginId) {
        log.info(">>>>>> logout = {}loginId=>", loginId);
        UserResponse.SelectByLoginId userResponse = userService.selectUserByLoginId(loginId);
        authTokenService.deleteAuthTokenByAccessToken(userResponse.getId());
        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }

    @GetMapping(value = "/logoutAuto")
    @Operation(summary = "자동 로그아웃")
    @AccessLog("자동 로그아웃")
    public ApiResponse logoutAuto(@Parameter(hidden = true) @JwtUser User jwtUser) {
        log.info(">>>>>> logoutAuto = {}loginId=>", jwtUser);
        UserResponse.SelectByLoginId userResponse = userService.selectUserByLoginId(jwtUser.getLoginId());
        authTokenService.deleteAuthTokenByAccessToken(userResponse.getId());
        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }

    @NotAuthRequired
    @PostMapping(value = "/login")
    @Operation(summary = "로그인 및 인증토큰 생성")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //log.info(">>>>>> loginRequest = {}", loginRequest);
        LoginResponse loginResponse = new LoginResponse();
        UserResponse.SelectByLoginId userResponse = verifyAccount(loginRequest);
        if (userResponse == null || BooleanValueCode.N.equals(userResponse.getIsExistIdPass())) {
            return new ApiResponse<>(ApiResultCode.NOT_MATCHED_USER, loginResponse);
        }

/*
        // otp 입력확인
        if (!(freePassUserId.indexOf(userResponse.getLoginId()) > -1)) { // 개발자 로그인 관련 임시
            if (!StringUtils.equals(userResponse.getOtpNo(), loginRequest.getOtpNo())) {
                return new ApiResponse<>(ApiResultCode.NOT_MATCHED_OTP, loginResponse);
            } else if (Integer.parseInt(GlobalConst.OTP_LIMIT.getCode()) < userResponse.getOtpSecond()) {
                return new ApiResponse<>(ApiResultCode.TIME_OUT_OTP, loginResponse);
            }
        }
*/

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
        updateUser.setLoginLanguage(loginRequest.getCountryCode());
        updateUser.setLastLoginDateTime(LocalDateTime.now());
        updateUser.setLoginFailCnt(0);
        updateUser.setOtpNo(loginRequest.getOtpNo());
        updateUser.setOtpFailCnt(0);
        userService.updateUser(updateUser);
//        userResponse.setLoginLanguage(loginRequest.getCountryCode());
//        userResponse.setLanguageCode(loginRequest.getCountryCode());

        // 쿠키생성
        CookieGenerator cg = new CookieGenerator();
        cg.setCookieName(GlobalConst.COUNTRY_COOKIE.getCode());
        cg.addCookie(response, loginRequest.getCountryCode());

        // locale 설정
        CommUtil.setLocale(request, response, loginRequest.getCountryCode());

        //--------------------------------------------------------------------------------
        // 최종 결과값 엔티티 생성
        //--------------------------------------------------------------------------------
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

        rt.setMaxAge(7 * 24 * 60 * 60);
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
    public ApiResponse sentOpt(@RequestBody LoginRequest request) {

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
        user.setOtpNo(otpNo);
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
        UserResponse.SelectByLoginId userResponse = userService.selectUserByLoginId(request.getLoginId());

        if (userResponse == null) {
            return null;
        }

        //--------------------------------------------------------------------------------
        // 비밀번호 확인
        //--------------------------------------------------------------------------------
        String encPassword = null;

        try {
            encPassword = CryptUtil.getHash(request.getPassword(), request.getLoginId().getBytes());

            //log.info("request.getPassword() : {}", request.getPassword());
            //log.info("encPassword : {}", encPassword);
            //log.info("userResponse.getLoginPass() : {}", userResponse.getLoginPass());

            if (userResponse.getLoginPass().equals(encPassword)) {
                userResponse.setIsExistIdPass(BooleanValueCode.Y);
            } else {
                UserRequest.Update userRequest = new UserRequest.Update();
                userRequest.setId(userResponse.getId());
                userRequest.setLoginFailCnt(userResponse.getLoginFailCnt() + 1); // 실패카운트 증가
                userRequest.setUpdateUser(userResponse.getLoginId());

                userResponse.setLoginFailCnt(userRequest.getLoginFailCnt());
                if (userRequest.getLoginFailCnt().compareTo(5) > 0) {
                    userRequest.setLockYn(BooleanValueCode.Y);
                    userResponse.setLockYn(BooleanValueCode.Y);
                }

                // 계정_수정
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
    public ApiResponse changePassword(@RequestBody LoginRequest request) {

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
            return new ApiResponse<>(ApiResultCode.PASS_FORMAT_ERR);
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
            user.setUpdateUser(userResponse.getLoginId());
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
    public ApiResponse stayPassword(@RequestBody LoginRequest request) {
        UserResponse.SelectByLoginId userResponse = userService.selectUserByLoginId(request.getLoginId());

        if (userResponse == null) {
            return new ApiResponse<>(ApiResultCode.NOT_MATCHED_NOW_PASS);
        }

        try {
            User user = new User();
            user.setLoginId(request.getLoginId());
            user.setUpdateUser(request.getLoginId());
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
    public ApiResponse passwordInit(@RequestBody LoginRequest request) {

        if (request != null && StringUtils.isEmpty(request.getLoginId())) {
            return new ApiResponse<>(ApiResultCode.NOT_LOGINID);
        }

        if (request != null && StringUtils.isEmpty(request.getCountryCode())) {
            return new ApiResponse<>(ApiResultCode.NOT_COUNTRY_CODE);
        }

        if (request != null && StringUtils.isEmpty(request.getPhoneNo())) {
            return new ApiResponse<>(ApiResultCode.NOT_PHONENO);
        }

        UserResponse.SelectByLoginId userResponse = userService.selectUserByLoginIdCountryCode(request.getLoginId(), request.getCountryCode());

        if (userResponse == null) {
            return new ApiResponse<>(ApiResultCode.NOT_REG_LOGINID);
        }

        if (StringUtils.isEmpty(userResponse.getPhoneNo())) {
            return new ApiResponse<>(ApiResultCode.NOT_REG_PHONENO);
        }

        if (!StringUtils.equals(userResponse.getPhoneNo().trim(), request.getPhoneNo().trim())) {
            return new ApiResponse<>(ApiResultCode.NOT_MATCH_REG_PHONENO);
        }

        if (CommUtil.isBadPhoneNo(userResponse.getPhoneNo())) {
            return new ApiResponse<>(ApiResultCode.NOT_FORM_PHONENO);
        }

//        String chgPass = CommUtil.getRamdomPassword();
        String chgPass = "1111"; // 일단 비밀번호 초기화
        String phone = userResponse.getPhoneNo();
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
                user.setUpdateUser(userResponse.getLoginId());
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

        if (menuUri.indexOf("/detail") > -1) {
            menuUri = menuUri.substring(0, menuUri.indexOf("/detail"));
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
}
