package com.shop.api.biz.system.controller;

import com.shop.api.annotation.AccessLog;
import com.shop.api.annotation.JwtUser;
import com.shop.api.biz.system.service.UserService;
import com.shop.api.biz.system.service.UserCodeService;
import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.biz.system.vo.response.ApiResponse;
import com.shop.core.entity.UserCode;
import com.shop.core.entity.User;
import com.shop.core.enums.ApiResultCode;
import com.shop.core.biz.system.dao.UserCodeDao;
import com.shop.core.biz.system.vo.request.UserCodeRequest;
import com.shop.core.biz.system.vo.response.UserCodeResponse;
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
 * Description: 코드_관리 Controller
 * Date: 2023/02/06 11:56 AM
 * Company: smart90
 * Author: luckeey
 * </pre>
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/userCode")
@Tag(name = "UserCodeController", description = "코드 관련 API")
public class UserCodeController {

    private final UserCodeService userCodeService;
    private final UserService userService;
    private final UserCodeDao userCodeDao;

    /**
     * 코드관리_목록_조회 (페이징)
     *
     * @param jwtUser
     * @param filter
     * @param pageRequest
     * @return
     */
    @AccessLog("코드관리 목록 조회")
    @GetMapping(value = "/paging")
    @Operation(summary = "코드관리 목록 조회 (페이징)")
    public ApiResponse<PageResponse<UserCodeResponse.Paging>> selectUserCodePaging(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(name = "UserCodeRequestPagingFilter", description = "코드관리 목록 조회 (페이징) 필터", in = ParameterIn.PATH) UserCodeRequest.PagingFilter filter,
            @Parameter(name = "PageRequest", description = "코드관리 목록 조회 페이징") PageRequest<UserCodeRequest.PagingFilter> pageRequest
    ) {
        // 상위코드가 없을 시, default 값 셋팅
        if (StringUtils.isEmpty(filter.getCodeUpper())) {
            filter.setCodeUpper("TOP");
        }

        pageRequest.setFilter(filter);

        // 코드관리_목록_조회 (페이징)
        PageResponse<UserCodeResponse.Paging> response = userCodeService.selectCodePaging(pageRequest);

        return new ApiResponse<>(ApiResultCode.SUCCESS, response);
    }

    /**
     * 코드_콤보_조회 (by CodeUpper)
     *
     * @param codeRequest
     * @return
     */
    @GetMapping(value = "/dropdown")
    @Operation(summary = "코드 콤보 조회")
    public ApiResponse<List<UserCodeResponse.UserCodeDropDown>> selectDropdownByUserCodeUpper(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(description = "코드 DropDown Request") UserCodeRequest.UserCodeDropDown codeRequest
    ) {
        // 필수값 체크
        if (StringUtils.isEmpty(codeRequest.getCodeUpper())) {
            return new ApiResponse<>(ApiResultCode.NO_REQUIRED_VALUE);
        }

        User user = userService.selectUserById(jwtUser.getId());
        if (user.getId() == null || user.getId() == 0) {
            return new ApiResponse<>(ApiResultCode.NO_REQUIRED_VALUE);
        }

        codeRequest.setUserId(user.getId());
        // 코드_콤보_조회 (by CodeUpper)
        List<UserCodeResponse.UserCodeDropDown> codeList = userCodeService.selectLowerCodeByUserCodeUpper(codeRequest);

        return new ApiResponse<>(codeList);
    }
    /**
     * 코드_콤보_조회 (by CodeUpper)
     *
     * @param codeRequest
     * @return
     */
    @GetMapping(value = "/lowerCodeList")
    @Operation(summary = "코드 콤보 조회")
    public ApiResponse<List<UserCodeResponse.LowerSelect>> selectLowerCodeByCodeUpperForUserCodeMng(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @ModelAttribute @Parameter(description = "코드 DropDown Request") UserCodeRequest.UserCodeDropDown codeRequest
    ) {
        // 필수값 체크
        if (StringUtils.isEmpty(codeRequest.getCodeUpper())) {
            return new ApiResponse<>(ApiResultCode.NO_REQUIRED_VALUE);
        }

        User user = userService.selectUserById(jwtUser.getId());

        // 필수값 체크
        if (user.getId() == null || user.getId() == 0) {
            return new ApiResponse<>(ApiResultCode.FAIL,"도매id 가 존재하지 않습니다.");
        }

        // 코드_콤보_조회 (by CodeUpper)
        codeRequest.setUserId(user.getId());
        List<UserCodeResponse.LowerSelect> codeList = userCodeService.selectLowerCodeByCodeUpperForUserCodeMng(codeRequest);

        if (codeList.isEmpty()) {
            return new ApiResponse<>(ApiResultCode.NOT_FOUND_CODE);
        }
        return new ApiResponse<>(codeList);
    }


    /**
     * 코드_조회 (by Id)
     *
     * @param upperCode
     * @return
     */
    @GetMapping(value = "/{upperCode}")
    @Operation(summary = "코드 조회")
    public ApiResponse<List<UserCodeResponse.LowerSelect>> selectUserCodeById(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(description = "코드_아이디") @PathVariable String upperCode
    ) {
        // 필수값 체크
        if (StringUtils.isEmpty(upperCode)) {
            return new ApiResponse<>(ApiResultCode.NO_REQUIRED_VALUE);
        }
        User user = userService.selectUserById(jwtUser.getId());

        UserCodeRequest.UserCodeDropDown userCodeRequest = new UserCodeRequest.UserCodeDropDown();
        userCodeRequest.setCodeUpper(upperCode);
        userCodeRequest.setUserId(user.getId());

        // 코드_조회 (by Id)
        List<UserCodeResponse.LowerSelect> list = userCodeService.selectLowerCodeByCodeUpperForUserCodeMng(userCodeRequest);

        if (list == null) {
            return new ApiResponse<>(ApiResultCode.NOT_FOUND_CODE);
        }

        return new ApiResponse<>(list);
    }

    /**
     * 코드_등록
     *
     * @param jwtUser
     * @param codeRequest
     * @return
     */
    @AccessLog("코드 등록")
    @PostMapping()
    @Operation(summary = "코드 등록 복수")
    public ApiResponse<ApiResultCode> saveUserCodes(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(description = "코드 등록 Request") @RequestBody UserCodeRequest.Create codeRequest
    ) {
        userCodeService.saveUserCodes(codeRequest, jwtUser);
        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }



    /**
     * 코드_콤보_조회 (by CodeUpper)
     *
     * @param codeRequest
     * @return
     */
    @PostMapping(value = "/updateUserCode")
    @Operation(summary = "코드정보 변경 단건")
    public ApiResponse updateUserCode(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @RequestBody @Parameter(description = "코드 변경 Request") UserCodeRequest.UpdateUserCodeVal codeRequest
    ) {
        userCodeService.saveUserCodeVal(codeRequest, jwtUser);
        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }

    /**
     * 코드 정렬순서(CODE_ORDER) 단건 변경 - 콤보 변경 즉시 반영용 (예: 시즌 순서)
     */
    @AccessLog("파트너코드 순서 변경")
    @PutMapping(value = "/order")
    @Operation(summary = "코드 정렬순서(CODE_ORDER) 단건 변경")
    public ApiResponse<Void> updateCodeOrder(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @RequestParam Integer id,
            @RequestParam Integer codeOrder
    ) {
        com.shop.core.entity.UserCode code = com.shop.core.entity.UserCode.builder()
                .id(id)
                .codeOrder(codeOrder)
                .updUser(jwtUser.getLoginId())
                .build();
        userCodeDao.updateUserCodeExistOnly(code);
        return new ApiResponse<>(ApiResultCode.SUCCESS, null);
    }

    /**
     * 코드_수정
     *
     * @param jwtUser
     * @param codeRequest
     * @return
     */
    @AccessLog("파트너코드 삭제")
    @DeleteMapping("")
    @Operation(summary = "파트너코드 삭제")
    public ApiResponse deleteUserCode(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(description = "코드 수정 Request") @RequestBody UserCodeRequest.Delete codeRequest
    ) {
        // 코드_조회 (by Uk)
        Integer updateCount = userCodeService.deleteCode(codeRequest);

        if (updateCount == 0) {
            return new ApiResponse<>(ApiResultCode.FAIL_DELETE);
        }

        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }

    @AccessLog("파트너코드 소프트삭제")
    @PutMapping("/update-status")
    @Operation(summary = "파트너코드 소프트삭제")
    public ApiResponse updateUserCodeToDeletedStatus(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(description = "코드 삭제상태 수정 Request")  @RequestBody UserCodeRequest.SoftDelete codeRequest
    ) {
        return new ApiResponse<>(ApiResultCode.SUCCESS, userCodeService.updateUserCodeToDeletedStatus(codeRequest, jwtUser));
    }
}
