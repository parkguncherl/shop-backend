package com.binblur.api.biz.mypage.controller;

import com.binblur.api.annotation.AccessLog;
import com.binblur.api.annotation.JwtUser;
import com.binblur.api.biz.mypage.service.MypageService;
import com.binblur.api.biz.system.service.UserService;
import com.binblur.core.biz.system.vo.request.UserRequest;
import com.binblur.core.biz.system.vo.response.ApiResponse;
import com.binblur.core.biz.system.vo.response.UserResponse;
import com.binblur.core.entity.User;
import com.binblur.core.enums.ApiResultCode;
import com.binblur.core.exception.EspRuntimeException;
import com.binblur.core.utils.CryptUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <pre>
 * Description : 마이페이지 Controller
 * Date : 2023/04/24 2:13 PM
 * Company : smart90
 * Author : luckeey
 * </pre>
 */
@Slf4j
@RestController
@RequestMapping("/mypage")
@Tag(name = "MypageController", description = "마이페이지 관련 API")
public class MypageController {

    @Autowired
    private MypageService mypageService;

    @Autowired
    private UserService userService;

    /**
     * 사용자 조회
     * @param jwtUser
     * @param id
     * @return
     */
    @AccessLog("사용자 조회")
    @GetMapping(value = "/{id}")
    @Operation(summary = "사용자 조회")
    public ApiResponse<User> selectUserById(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(name = "id", description = "사용자 아이디") @PathVariable Integer id) {
        if (!id.equals(jwtUser.getId())) {
            throw new EspRuntimeException(ApiResultCode.NOT_AVAILABLE_ACCESS);
        };
        User user = mypageService.selectUserById(id);
        if (user == null) {
            throw new EspRuntimeException(ApiResultCode.NOT_FOUND_USER);
        }
        return new ApiResponse<>(ApiResultCode.SUCCESS, user);
    }

    /**
     * 사용자 수정
     * @return
     */
    @AccessLog("사용자 수정")
    @PutMapping(value = "/{id}")
    @Operation(summary = "사용자 수정")
    public ApiResponse<Integer> updateUser(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(name = "id", description = "사용자 아이디") @PathVariable Integer id,
            @Parameter(description = "사용자 수정 Request") @RequestBody UserRequest.Update userRequest
    ) {
        userRequest.setId(id);
        userRequest.setUpdateUser(jwtUser.getLoginId());
        UserResponse.SelectByLoginId user = userService.selectUserByLoginId(userRequest.getLoginId());
        if (user == null) {
            throw new EspRuntimeException(ApiResultCode.NOT_FOUND_USER);
        }
        Integer count = mypageService.updateUser(userRequest);
        if (count == null || count == 0) {
            throw new EspRuntimeException(ApiResultCode.FAIL_UPDATE);
        }
        return new ApiResponse<>(ApiResultCode.SUCCESS_CHANGE_USER_INFO, count);
    }

    /**
     * 현재 비밀번호 확인
     * @return
     */
    @PostMapping(value = "/validate/password")
    @Operation(summary = "현재 비밀번호 확인")
    public ApiResponse<Boolean> validatePassword(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(description = "현재 비밀번호 확인 요청 Request") @RequestBody UserRequest.ValidatePassword userRequest
    ) {
        User currentUser = userService.selectUserById(userRequest.getId());
        if (currentUser == null) {
            throw new EspRuntimeException(ApiResultCode.NOT_FOUND_USER);
        }
        String encPassword = null;
        try {
            encPassword = CryptUtil.getHash(userRequest.getCurrentPass(), userRequest.getLoginId().getBytes());
            // 기존 비밀번호와 일치할 경우
            if (currentUser.getLoginPass().equals(encPassword)) {
                return new ApiResponse<>(ApiResultCode.SUCCESS, true);
            }
            return new ApiResponse<>(ApiResultCode.SUCCESS, false);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 비밀번호 변경
     * @return
     */
    @PutMapping(value = "/update/password")
    @Operation(summary = "비밀번호 변경")
    public ApiResponse<Boolean> updatePassword(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(description = "비밀번호 변경 요청 Request") @RequestBody UserRequest.UpdatePassword userRequest
    ) {
        User currentUser = userService.selectUserById(userRequest.getId());
        if (currentUser == null) {
            throw new EspRuntimeException(ApiResultCode.NOT_FOUND_USER);
        }
        String encPassword = null;
        try {
            encPassword = CryptUtil.getHash(userRequest.getNextPass(), userRequest.getLoginId().getBytes());
            currentUser.setLoginPass(encPassword);
            currentUser.setUpdateUser(jwtUser.getLoginId());
            Integer count = userService.updatePassword(currentUser);
            if (count == null || count == 0) {
                throw new EspRuntimeException(ApiResultCode.FAIL_UPDATE);
            }
            return new ApiResponse<>(ApiResultCode.SUCCESS, true);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
