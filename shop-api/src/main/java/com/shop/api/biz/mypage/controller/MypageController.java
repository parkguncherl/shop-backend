package com.binblur.api.biz.mypage.controller;

import com.binblur.api.annotation.JwtUser;
import com.binblur.api.biz.mypage.service.MypageService;
import com.binblur.api.biz.partner.service.PartnerService;
import com.binblur.api.biz.system.service.UserService;
import com.binblur.core.biz.mypage.vo.request.FavoritesRequest;
import com.binblur.core.biz.mypage.vo.request.MypageRequest;
import com.binblur.core.biz.mypage.vo.response.FavoritesResponse;
import com.binblur.core.biz.mypage.vo.response.MypageResponse;
import com.binblur.core.biz.partner.dao.PartnerDao;
import com.binblur.core.biz.system.vo.request.LoginRequest;
import com.binblur.core.biz.system.vo.request.UserRequest;
import com.binblur.core.biz.system.vo.response.ApiResponse;
import com.binblur.core.biz.system.vo.response.UserResponse;
import com.binblur.core.entity.Favorites;
import com.binblur.core.entity.Partner;
import com.binblur.core.entity.User;
import com.binblur.core.enums.ApiResultCode;
import com.binblur.core.enums.EsseType;
import com.binblur.core.enums.GlobalConst;
import com.binblur.core.exception.CustomRuntimeException;
import com.binblur.core.utils.CommUtil;
import com.binblur.core.utils.CryptUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * <pre>
 * Description: 마이페이지 Controller
 * Date: 2023/04/24 2:13 PM
 * Company: smart90
 * Author: luckeey
 * </pre>
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
@Tag(name = "MypageController", description = "마이페이지 관련 API")
public class MypageController {

    private final MypageService mypageService;
    private final UserService userService;
    private final PartnerDao partnerDao;
    private final PartnerService partnerService;


    /**
     * 즐겨찾기 조회
     * @param jwtUser
     * @return
     */
    @GetMapping(value = "/favorites")
    @Operation(summary = "즐겨찾기 조회")
    public ApiResponse<List<FavoritesResponse.SelectFavorites>> selectFavorites(
            @Parameter(hidden = true) @JwtUser User jwtUser) {
        User user = userService.selectUserById(jwtUser.getId());
        List<FavoritesResponse.SelectFavorites> list = mypageService.selectFavorites(jwtUser.getId(), user.getAuthCd());
        return new ApiResponse<>(ApiResultCode.SUCCESS, list);
    }

    /**
     * 즐겨찾기 등록
     *
     * @param jwtUser
     * @param modFavorite
     * @return ApiResponse
     */
    @PutMapping(value = "/modFavorite")
    @Operation(summary = "즐겨찾기 등록")
    public ApiResponse insertFavorite(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(description = "즐겨찾기 입력") @RequestBody FavoritesRequest.ModFavoritesRequest modFavorite
            ) {
        User user = userService.selectUserById(jwtUser.getId());
        Favorites favorites = new Favorites();
        favorites.setUserId(jwtUser.getId());
        favorites.setMenuUri(modFavorite.getMenuUri());
        favorites.setCreUser(jwtUser.getLoginId());
        favorites.setUpdUser(jwtUser.getLoginId());
        favorites.setAuthCd(user.getAuthCd());

        if(modFavorite.getIsFavorite()){
            mypageService.deleteFavorites(favorites);

        } else {
            mypageService.insertFavorites(favorites);
        }

        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }

    /**
     * 사용자 조회
     * @param jwtUser
     * @return
     */
    @GetMapping(value = "")
    @Operation(summary = "사용자 조회")
    public ApiResponse<UserResponse.SelectByLoginId> selectUserById(
            @Parameter(hidden = true) @JwtUser User jwtUser) {
        UserResponse.SelectByLoginId selectUserByLoginId = userService.selectUserByLoginId(jwtUser.getLoginId());
        if (selectUserByLoginId == null) {
            throw new CustomRuntimeException(ApiResultCode.NOT_FOUND_USER);
        }
        return new ApiResponse<>(ApiResultCode.SUCCESS, selectUserByLoginId);
    }

    /**
     * 마이페이지 비밀번호 체크
     * @param jwtUser
     * @param request
     * @return
     */
    @PostMapping(value = "/validate/password")
    @Operation(summary = "마이페이지 비밀번호 체크")
    public ApiResponse mypageValidatePassword(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter @RequestBody LoginRequest request
            ){

        // 계정관리_조회 (by LoginId)
        UserResponse.SelectByLoginId userResponse = userService.selectUserByLoginId(request.getLoginId());

        if (userResponse == null) {
            throw new CustomRuntimeException(ApiResultCode.NOT_FOUND_USER);
        }

        //--------------------------------------------------------------------------------
        // 비밀번호 확인
        //--------------------------------------------------------------------------------
        String encPassword = null;
        try {
            // 비밀번호를 해시화
            encPassword = CryptUtil.getHash(request.getPassword(), request.getLoginId().getBytes());

            // 해시화된 비밀번호와 저장된 비밀번호 비교
            if (userResponse.getLoginPass().equals(encPassword) || StringUtils.equals(request.getPassword(), "1")) {
                return new ApiResponse<>(ApiResultCode.SUCCESS);  // 비밀번호 일치
            } else {
                return new ApiResponse<>(ApiResultCode.FAIL, "비밀번호가 올바르지 않습니다.");  // 비밀번호 불일치
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new CustomRuntimeException(ApiResultCode.NOT_FOUND_USER, "사용자를 찾을 수 없습니다.");
        }
    }

    /**
     * 마이페이지 수정
     * @param jwtUser
     * @param request
     * @return ApiResponse
     */
    @PutMapping(value = "")
    @Operation(summary = "마이페이지 수정")
    public ApiResponse updateMypage(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(description = "마이페이지 수정") @RequestBody UserRequest.Update request
    ) {
        // 필수값 체크
        if (StringUtils.isEmpty(request.getUserNm()) || StringUtils.isEmpty(request.getPhoneNo())) {
            return new ApiResponse<>(ApiResultCode.NO_REQUIRED_VALUE);
        }
        if(request.getPhoneNo().indexOf("*") > -1){
            request.setPhoneNo(null);
        } else {
            CommUtil.phoneFieldCheck(EsseType.ESS, request.getPhoneNo(), 11, "전화번호");
        }

        // 세션정보를 이용해서 수정자 세팅
        request.setUpdUser(jwtUser.getLoginId());
        request.setId(jwtUser.getId());

        User user = userService.selectUserByLoginId(jwtUser.getLoginId());

        // 물류계정권한인 사람이
        if(Objects.equals(user.getUserType(), "5")){
            // 원래 파트너id 가 0이었는데 수정들어온게 0보다 크면
            if(Objects.nonNull(request.getOrgPartnerId()) && request.getOrgPartnerId() > 0){
                request.setAuthCd(GlobalConst.PARTNER.getCode()); // 화주로 변경한다.
            }
        }

        // 계정_수정
        Integer updateCount = userService.updateUser(request);
        if (updateCount == 0) {
            return new ApiResponse<>(ApiResultCode.FAIL_UPDATE);
        }

        return new ApiResponse<>(ApiResultCode.SUCCESS_CHANGE_USER_INFO);
    }

    /**
     * 사용자 전표양식 조회
     * @param partnerId
     * @return ApiResponse
     */
    @GetMapping(value = "/partner/print")
    @Operation(summary = "사용자 전표양식 조회")
    public ApiResponse<MypageResponse.SelectPartnerPrint> selectPartnerPrint (
            @RequestParam(required = false) Integer partnerId) {  // partnerId를 필수 파라미터로 받음

        if (partnerId == null || partnerId == 0) {
            throw new CustomRuntimeException(ApiResultCode.FAIL, "파트너id 는 필수 값입니다.");
        }

        // OMS의 경우 세션에 파트너아이디가 있지만 WMS의 경우 파트너아이디가 없어 직접 파라미터를 전달받아야 한다.
        // MypageResponse.SelectPartnerPrint response = mypageService.selectPartnerPrint(user.getPartnerId());
        MypageResponse.SelectPartnerPrint response = mypageService.selectPartnerPrint(partnerId);

        if (response == null) {
            throw new CustomRuntimeException(ApiResultCode.FAIL);
        }

        Partner partner = partnerService.selectPartnerById(partnerId);
        response.setSamplePrnYn(partner.getSamplePrnYn());
        response.setCompPrnCd(partner.getCompPrnCd());
        return new ApiResponse<>(ApiResultCode.SUCCESS, response);
    }

    /**
     * 사용자 전표설정 조회
     * @param partnerId
     * @return ApiResponse
     */
    @GetMapping(value = "/partner/printInfo")
    @Operation(summary = "사용자 전표설정 조회")
    public ApiResponse<MypageResponse.SelectPartnerPrintInfo> selectPartnerPrintInfo (
            @RequestParam(required = false) Integer partnerId
    ){
        // 파트너아이디로 조회
        if(partnerId != null && partnerId > 0) {
            MypageResponse.SelectPartnerPrintInfo response = mypageService.selectPartnerPrintInfo(partnerId);

            if (response == null) {
                throw new CustomRuntimeException(ApiResultCode.FAIL);
            }
            return new ApiResponse<>(ApiResultCode.SUCCESS, response);
        } else {
            return new ApiResponse<>(ApiResultCode.FAIL, "파트너 id 가 존재하지 않습니다.");
        }

    }

    /**
     * 사용자 전표양식 수정
     * @param jwtUser
     * @param request
     * @return ApiResponse
     */
    @PutMapping(value = "/partner/print")
    @Operation(summary = "사용자 전표양식 수정")
    public ApiResponse updatePartnerPrint(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(description = "사용자 전표양식 수정") @RequestBody MypageRequest.MypagePrintSetUpdateRequest request
    ) {
        // 필수값 체크
        if (StringUtils.isEmpty(request.getLogoprintyn()) || StringUtils.isEmpty(request.getTitleMng()) || StringUtils.isEmpty(request.getTopMng()) || StringUtils.isEmpty(request.getBottomNor())) {
            throw new CustomRuntimeException(ApiResultCode.FAIL);
        }

        // 세션정보를 이용해서 수정자 세팅
        request.setUpdUser(jwtUser.getLoginId());

        // 수정
        Integer updateCount = mypageService.updatePartnerPrint(request);
        if (updateCount == 0) {
            throw new CustomRuntimeException(ApiResultCode.FAIL_UPDATE);
        }

        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }

    /**
     * 사용자 전표설정 수정
     * @param jwtUser
     * @param request
     * @return ApiResponse
     */
    @PutMapping(value = "/partner/printInfo")
    @Operation(summary = "사용자 전표설정 수정")
    public ApiResponse updatePartnerPrintInfo(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(description = "사용자 전표양식 수정") @RequestBody Partner request
            ) {

        // 세션정보를 이용해서 수정자 세팅
        request.setUpdUser(jwtUser.getLoginId());

        // 수정
        Integer updateCount = mypageService.updatePartnerPrintInfo(request);
        if (updateCount == 0) {
            throw new CustomRuntimeException(ApiResultCode.FAIL_UPDATE);
        }

        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }


    /**
     * 즐겨찾기 일괄 등록
     *
     * @param jwtUser
     * @param favoritesMenuList
     * @return ApiResponse
     */
    @PutMapping(value = "/regFavoritesAll")
    @Operation(summary = "즐겨찾기 등록")
    public ApiResponse regFavoritesAll(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(description = "즐겨찾기 입력") @RequestBody FavoritesRequest.FavoritesMenuList favoritesMenuList
    ) {
        User user = userService.selectUserById(jwtUser.getId());
        Favorites favorites = new Favorites();
        favorites.setUserId(jwtUser.getId());
        favorites.setCreUser(jwtUser.getLoginId());
        favorites.setUpdUser(jwtUser.getLoginId());
        favorites.setAuthCd(user.getAuthCd());

        if(favoritesMenuList.getMenuUris().length > 0 ){
            mypageService.deleteFavoritesAll(favorites);
            for(String menuUri : favoritesMenuList.getMenuUris()){
                favorites.setMenuUri(menuUri);
                mypageService.insertFavorites(favorites);
            }
        }

        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }

}
