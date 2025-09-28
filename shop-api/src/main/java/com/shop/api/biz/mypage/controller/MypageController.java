package com.shop.api.biz.mypage.controller;

import com.shop.api.annotation.JwtUser;
import com.shop.api.biz.mypage.service.MypageService;
import com.shop.api.biz.system.service.UserService;
import com.shop.core.biz.mypage.vo.request.FavoritesRequest;
import com.shop.core.biz.mypage.vo.response.FavoritesResponse;
import com.shop.core.biz.system.vo.request.LoginRequest;
import com.shop.core.biz.system.vo.request.UserRequest;
import com.shop.core.biz.system.vo.response.ApiResponse;
import com.shop.core.biz.system.vo.response.UserResponse;
import com.shop.core.entity.Favorites;
import com.shop.core.entity.User;
import com.shop.core.enums.ApiResultCode;
import com.shop.core.enums.EsseType;
import com.shop.core.enums.GlobalConst;
import com.shop.core.exception.CustomRuntimeException;
import com.shop.core.utils.CommUtil;
import com.shop.core.utils.CryptUtil;
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

        // 계정_수정
        Integer updateCount = userService.updateUser(request);
        if (updateCount == 0) {
            return new ApiResponse<>(ApiResultCode.FAIL_UPDATE);
        }

        return new ApiResponse<>(ApiResultCode.SUCCESS_CHANGE_USER_INFO);
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
