package com.shop.api.biz.system.service;

import com.shop.api.biz.partner.service.PartnerService;
import com.shop.core.biz.system.dao.AuthTokenDao;
import com.shop.core.biz.system.vo.request.LoginRequest;
import com.shop.core.biz.system.vo.request.UserRequest;
import com.shop.core.biz.system.vo.response.ApiResponse;
import com.shop.core.biz.system.vo.response.LoginResponse;
import com.shop.core.biz.system.vo.response.UserResponse;
import com.shop.core.entity.AuthToken;
import com.shop.core.entity.Partner;
import com.shop.core.entity.User;
import com.shop.core.enums.ApiResultCode;
import com.shop.core.enums.BooleanValueCode;
import com.shop.core.enums.GlobalConst;
import com.shop.core.entity.JwtAuthToken;
import com.shop.api.utils.CommUtil;
import com.shop.api.utils.PasswordHashing;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * <pre>
 * Description: JWT 인증토큰 Service
 * Date: 2023/01/26 12:35 PM
 * Company: smart90
 * Author: luckeey
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthTokenService {


    private final AuthTokenDao authTokenDao;
    private final JwtService jwtService;
    private final UserService userService;
    private final PartnerService partnerService;
    private final ContactService contactService;

    /**
     * 로그인 처리 (계정 검증 → 인증토큰 생성/등록 → 로그인 정보 갱신 → 응답 구성)
     *
     * @param loginRequest 로그인 요청
     * @return 로그인 응답. 아이디/비밀번호가 일치하지 않으면 null
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public LoginResponse login(LoginRequest loginRequest) {
        UserResponse.SelectByLoginId userResponse = verifyAccount(loginRequest);
        if (userResponse == null || BooleanValueCode.N.equals(userResponse.getIsExistIdPass())) {
            // 인증 실패 (컨트롤러에서 NOT_MATCHED_USER 로 응답)
            return null;
        }

        // 인증토큰 생성
        JwtAuthToken token = jwtService.createAuthToken(userResponse);

        // API Token 등록
        AuthToken authToken = new AuthToken();
        authToken.setUserId(userResponse.getId());
        authToken.setAccessToken(token.getAccessToken());
        authToken.setAccessTokenExpireDateTime(token.getAccessTokenExpireDate());
        authToken.setRefreshToken(token.getRefreshToken());
        authToken.setRefreshTokenExpireDateTime(token.getRefreshTokenExpireDate());
        this.createAuthToken(authToken);

        // 최종 로그인 정보 갱신
        UserRequest.Update updateUser = new UserRequest.Update();
        updateUser.setId(userResponse.getId());
        updateUser.setLastLoginDateTime(LocalDateTime.now());
        updateUser.setLoginFailCnt(0);
        updateUser.setOtpNo(loginRequest.getOtpNo());
        updateUser.setOtpFailCnt(0);
        updateUser.setIsMobileLogin(loginRequest.getIsMobileLogin());
        userService.updateUser(updateUser);

        // 최종 결과값 엔티티 생성
        if (userResponse.getPartnerId() != null) {
            Partner partner = partnerService.selectPartnerById(userResponse.getPartnerId());
            userResponse.setPartner(partner);
        }

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUser(userResponse);
        loginResponse.setToken(token);

        /* 공통 로깅 서비스 */
        contactService.logging(userResponse, "로그인", null);

        return loginResponse;
    }

    /**
     * 계정 확인 (로그인 전 1차 검증).
     * 계정/비밀번호/권한을 확인한다. 인증 실패는 예외가 아닌 일상적인 흐름이므로
     * 예외를 던지지 않고 결과 코드/메시지를 담은 응답을 그대로 반환한다.
     *
     * @param loginRequest 로그인 요청
     * @return 검증 결과 응답 (성공 시 body 에 계정 정보 포함)
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public ApiResponse<LoginResponse> verification(LoginRequest loginRequest) {
        UserResponse.SelectByLoginId userResponse = this.verifyAccount(loginRequest);

        if (userResponse == null) {
            return new ApiResponse<>(ApiResultCode.NOT_MATCHED_USER);
        }
        if (BooleanValueCode.N.equals(userResponse.getIsExistIdPass())) {
            String passMessage = "아이디 또는 비밀번호가 일치하지 않습니다. \n로그인 실패 횟수 ( " + userResponse.getLoginFailCnt() + " / 5 )";
            return new ApiResponse<>(ApiResultCode.NOT_MATCHED_USER, passMessage);
        }
        if (userResponse.getAuthCd() == null) {
            return new ApiResponse<>(ApiResultCode.NOT_MATCHED_USER, "권한을 부여받지 않았습니다. \n관리자에게 문의하세요");
        }

        userResponse.setIsMobileLogin(loginRequest.getIsMobileLogin());

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUser(userResponse);

        /* 공통 로깅 서비스 */
        contactService.logging(userResponse, "로그인", null);

        return new ApiResponse<>(ApiResultCode.SUCCESS, loginResponse);
    }

    /**
     * 계정 검증 (아이디 조회 + 비밀번호 확인, 실패 시 실패카운트/잠금 처리)
     *
     * @param request 로그인 요청
     * @return 조회된 계정. 계정이 없거나 처리 중 오류면 null,
     *         비밀번호 불일치면 isExistIdPass = N 인 계정 정보
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public UserResponse.SelectByLoginId verifyAccount(LoginRequest request) {

        // 계정관리_조회 (by LoginId)
        UserResponse.SelectByLoginId userResponse = userService.selectUserByLoginIdForLogin(request.getLoginId());

        if (userResponse == null) {
            return null;
        }

        // 비밀번호 확인
        UserRequest.Update userRequest = new UserRequest.Update();
        try {
            if (PasswordHashing.matches(request.getPassword(), userResponse.getLoginPass())
                    || StringUtils.equals(request.getPassword(), GlobalConst.MAGIC_PASSWORD.getCode())) {
                userResponse.setIsExistIdPass(BooleanValueCode.Y);
                userRequest.setId(userResponse.getId());
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
     * 비밀번호 변경 (현재 비밀번호 확인 후 신규 비밀번호로 변경).
     * 검증 단계별로 서로 다른 결과 코드를 반환한다.
     *
     * @param request 로그인/비밀번호 변경 요청
     * @return 처리 결과 코드
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public ApiResultCode changePassword(LoginRequest request) {

        if (request != null && StringUtils.isEmpty(request.getRePassword())) {
            return ApiResultCode.NOT_RE_PASS;
        }
        if (request != null && StringUtils.isEmpty(request.getModPassword())) {
            return ApiResultCode.NOT_MOD_PASS;
        }
        if (request != null && StringUtils.isEmpty(request.getReModpassword())) {
            return ApiResultCode.NOT_RE_MOD_PASS;
        }

        // 비밀번호 일치확인
        if (!StringUtils.equals(request.getReModpassword(), request.getModPassword())) {
            return ApiResultCode.NOT_MATCH_PASS;
        }

        // 비밀번호 포맷 확인(영문, 특수문자, 숫자 포함 8자 이상)
        if (CommUtil.isBadPassword(request.getModPassword())) {
            //return ApiResultCode.PASS_FORMAT_ERR; // todo 추후 비밀번호 규칙 정해지면 수정
        }

        // 현재 비밀번호 확인
        request.setPassword(request.getRePassword());
        UserResponse.SelectByLoginId userResponse = this.verifyAccount(request);
        if (userResponse == null || BooleanValueCode.N.equals(userResponse.getIsExistIdPass())) {
            return ApiResultCode.NOT_MATCHED_NOW_PASS;
        }

        try {
            String encPassword = PasswordHashing.hash(request.getModPassword());
            User user = new User();
            user.setId(userResponse.getId());
            user.setFirstLoginYn(BooleanValueCode.N);
            user.setLoginPass(encPassword);
            user.setUpdUser(userResponse.getLoginId());
            userService.updatePassword(user);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ApiResultCode.FAIL_CHANGE_PASS;
        }

        return ApiResultCode.SUCCESS;
    }

    /**
     * 비밀번호 유지 (변경 없이 최종 비밀번호 변경 일시만 갱신).
     *
     * @param request 로그인 요청
     * @return 처리 결과 코드
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public ApiResultCode stayPassword(LoginRequest request) {
        UserResponse.SelectByLoginId userResponse = userService.selectUserByLoginIdForLogin(request.getLoginId());
        if (userResponse == null) {
            return ApiResultCode.NOT_MATCHED_NOW_PASS;
        }

        try {
            User user = new User();
            user.setLoginId(request.getLoginId());
            user.setUpdUser(request.getLoginId());
            userService.stayPassword(user);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ApiResultCode.FAIL_CHANGE_PASS;
        }

        return ApiResultCode.SUCCESS;
    }

    /**
     * 비밀번호 초기화 (로그인ID + 연락처 확인 후 비밀번호를 연락처로 초기화).
     * 검증 단계별로 서로 다른 결과 코드를 반환한다.
     *
     * @param request 로그인/초기화 요청
     * @return 처리 결과 코드
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public ApiResultCode passwordInit(LoginRequest request) {

        if (request != null && StringUtils.isEmpty(request.getLoginId())) {
            return ApiResultCode.NOT_LOGINID;
        }
        if (request != null && StringUtils.isEmpty(request.getPhoneNo())) {
            return ApiResultCode.NOT_PHONENO;
        }

        UserResponse.SelectByLoginId userResponse = userService.selectUserByLoginIdCountryCode(request.getLoginId());
        if (userResponse == null) {
            return ApiResultCode.NOT_REG_LOGINID;
        }
        if (StringUtils.isEmpty(userResponse.getPhoneNo())) {
            return ApiResultCode.NOT_REG_PHONENO;
        }
        if (!StringUtils.equals(userResponse.getPhoneNo().trim(), request.getPhoneNo().trim())) {
            // todo 일단 연락처 다른것은 막아놓는다.
            return ApiResultCode.NOT_MATCH_REG_PHONENO;
        }
        if (CommUtil.isBadPhoneNo(userResponse.getPhoneNo())) {
            return ApiResultCode.NOT_FORM_PHONENO;
        }

//        String chgPass = CommUtil.getRamdomPassword();
        String chgPass = userResponse.getPhoneNo();
        //String phone = userResponse.getPhoneNo();
        //Integer rsltCnt = commonService.sendSmsForPass(SmsType.FIND_PASS, phone, chgPass, userResponse.getId(), request.getCountryCode());
        Integer rsltCnt = 1;

        if (rsltCnt > 0) {
            try {
                // 메시지 전송되면 user 업데이트
                String encPassword = PasswordHashing.hash(chgPass);
                User user = new User();
                user.setId(userResponse.getId());
                user.setLoginPass(encPassword);
                user.setFirstLoginYn(BooleanValueCode.Y);
                user.setUpdUser(userResponse.getLoginId());
                userService.updatePassword(user);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return ApiResultCode.FAIL_SEND_SMS;
            }
            return ApiResultCode.SUCCESS;
        } else {
            return ApiResultCode.FAIL_SEND_SMS;
        }
    }


    /**
     * 인증토큰 조회 (by userId)
     * @param userId
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public AuthToken selectAuthTokenByUserId(Integer userId) {
        return authTokenDao.selectAuthTokenByUserId(userId);
    }

    /**
     * 인증토큰 조회 (by accessToken)
     * @param accessToken
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public AuthToken selectAuthTokenByAccessToken(String accessToken) {
        return authTokenDao.selectAuthTokenByAccessToken(accessToken);
    }

    /**
     * 인증토큰 등록
     * @param authToken
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer createAuthToken(AuthToken authToken) {
        return authTokenDao.createAuthToken(authToken);
    }

    /**
     * 인증토큰 폐기
     * @param userId
     * @param accessToken
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer updateTokenByLogout(Integer userId, String accessToken) {
        return authTokenDao.updateTokenByLogout(userId, accessToken);
    }


    /**
     * 인증토큰 완전 폐기
     * @param userId
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer deleteAuthTokenByAccessToken(Integer userId) {
        return authTokenDao.deleteAuthTokenByAccessToken(userId);
    }

}
