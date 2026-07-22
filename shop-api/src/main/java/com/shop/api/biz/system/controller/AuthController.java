package com.shop.api.biz.system.controller;

import com.shop.api.annotation.AccessLog;
import com.shop.api.annotation.JwtUser;
import com.shop.api.biz.system.service.*;
import com.shop.core.annotations.NotAuthRequired;
import com.shop.core.biz.system.vo.request.LoginRequest;
import com.shop.core.biz.system.vo.response.ApiResponse;
import com.shop.core.biz.system.vo.response.AuthResponse;
import com.shop.core.biz.system.vo.response.LoginResponse;
import com.shop.core.biz.system.vo.response.UserResponse;
import com.shop.core.entity.AuthToken;
import com.shop.core.entity.JwtAuthToken;
import com.shop.core.entity.User;
import com.shop.core.enums.*;
import com.shop.api.utils.CommUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

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

    private final MenuService menuService;

    @NotAuthRequired
    @PostMapping(value = "/verification")
    @Operation(summary = "계정 확인")
    public ApiResponse<LoginResponse> verification(@RequestBody LoginRequest loginRequest) {
        return authTokenService.verification(loginRequest);
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
        LoginResponse loginResponse = authTokenService.login(loginRequest);
        if (loginResponse == null) {
            // 아이디/비밀번호 불일치 (기존 응답 형태 유지를 위해 빈 LoginResponse 를 body 로 반환)
            return new ApiResponse<>(ApiResultCode.NOT_MATCHED_USER, new LoginResponse());
        }
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
     * 비밀번호 변경 비밀번호 변경 이전비밀번호 변경비밀번호 확인
     *
     * @return void
     */
    @NotAuthRequired
    @PostMapping(value = "/updatePassword")
    @Operation(summary = "비밀번호 전송")
    public ApiResponse<ApiResultCode> changePassword(@RequestBody LoginRequest request) {
        return new ApiResponse<>(authTokenService.changePassword(request));
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
        return new ApiResponse<>(authTokenService.stayPassword(request));
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
        return new ApiResponse<>(authTokenService.passwordInit(request));
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
    public ApiResponse<AuthResponse.MenuAuth> checkAuthMenu(@Parameter(hidden = true) @JwtUser User jwtUser,
                                                            @RequestParam("menuUri") String menuUri
    ) {
        return new ApiResponse<>(menuService.checkAuthMenu(jwtUser, menuUri));
    }

}
