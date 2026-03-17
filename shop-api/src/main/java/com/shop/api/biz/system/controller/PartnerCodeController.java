package com.shop.api.biz.system.controller;

import com.shop.api.annotation.AccessLog;
import com.shop.api.annotation.JwtUser;
import com.shop.api.biz.system.service.UserService;
import com.shop.api.biz.system.service.PartnerCodeService;
import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.biz.system.vo.response.ApiResponse;
import com.shop.core.entity.PartnerCode;
import com.shop.core.entity.User;
import com.shop.core.enums.ApiResultCode;
import com.shop.core.biz.system.dao.PartnerCodeDao;
import com.shop.core.biz.system.vo.request.PartnerCodeRequest;
import com.shop.core.biz.system.vo.response.PartnerCodeResponse;
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
@RequestMapping("/partnerCode")
@Tag(name = "PartnerCodeController", description = "코드 관련 API")
public class PartnerCodeController {

    private final PartnerCodeService partnerCodeService;
    private final UserService userService;
    private final PartnerCodeDao partnerCodeDao;

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
    public ApiResponse<PageResponse<PartnerCodeResponse.Paging>> selectPartnerCodePaging(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(name = "PartnerCodeRequestPagingFilter", description = "코드관리 목록 조회 (페이징) 필터", in = ParameterIn.PATH) PartnerCodeRequest.PagingFilter filter,
            @Parameter(name = "PageRequest", description = "코드관리 목록 조회 페이징") PageRequest<PartnerCodeRequest.PagingFilter> pageRequest
    ) {
        // 상위코드가 없을 시, default 값 셋팅
        if (StringUtils.isEmpty(filter.getCodeUpper())) {
            filter.setCodeUpper("TOP");
        }

        pageRequest.setFilter(filter);

        // 코드관리_목록_조회 (페이징)
        PageResponse<PartnerCodeResponse.Paging> response = partnerCodeService.selectCodePaging(pageRequest);

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
    public ApiResponse<List<PartnerCodeResponse.PartnerCodeDropDown>> selectDropdownByPartnerCodeUpper(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(description = "코드 DropDown Request") PartnerCodeRequest.PartnerCodeDropDown codeRequest
    ) {
        // 필수값 체크
        if (StringUtils.isEmpty(codeRequest.getCodeUpper())) {
            return new ApiResponse<>(ApiResultCode.NO_REQUIRED_VALUE);
        }

        User user = userService.selectUserById(jwtUser.getId());
        if (user.getPartnerId() == null || user.getPartnerId() == 0) {
            return new ApiResponse<>(ApiResultCode.NO_REQUIRED_VALUE);
        }

        codeRequest.setPartnerId(user.getPartnerId());
        // 코드_콤보_조회 (by CodeUpper)
        List<PartnerCodeResponse.PartnerCodeDropDown> codeList = partnerCodeService.selectLowerCodeByPartnerCodeUpper(codeRequest);

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
    public ApiResponse<List<PartnerCodeResponse.LowerSelect>> selectLowerCodeByCodeUpperForPartnerCodeMng(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(description = "코드 DropDown Request") PartnerCodeRequest.PartnerCodeDropDown codeRequest
    ) {
        // 필수값 체크
        if (StringUtils.isEmpty(codeRequest.getCodeUpper())) {
            return new ApiResponse<>(ApiResultCode.NO_REQUIRED_VALUE);
        }

        User user = userService.selectUserById(jwtUser.getId());

        // 필수값 체크
        if (user.getPartnerId() == null || user.getPartnerId() == 0) {
            return new ApiResponse<>(ApiResultCode.FAIL,"도매id 가 존재하지 않습니다.");
        }

        // 코드_콤보_조회 (by CodeUpper)
        codeRequest.setPartnerId(user.getPartnerId());
        List<PartnerCodeResponse.LowerSelect> codeList = partnerCodeService.selectLowerCodeByCodeUpperForPartnerCodeMng(codeRequest);

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
    public ApiResponse<List<PartnerCodeResponse.LowerSelect>> selectPartnerCodeById(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(description = "코드_아이디") @PathVariable String upperCode
    ) {
        // 필수값 체크
        if (StringUtils.isEmpty(upperCode)) {
            return new ApiResponse<>(ApiResultCode.NO_REQUIRED_VALUE);
        }
        User user = userService.selectUserById(jwtUser.getId());

        PartnerCodeRequest.PartnerCodeDropDown partnerCodeRequest = new PartnerCodeRequest.PartnerCodeDropDown();
        partnerCodeRequest.setCodeUpper(upperCode);
        partnerCodeRequest.setPartnerId(user.getPartnerId());

        // 코드_조회 (by Id)
        List<PartnerCodeResponse.LowerSelect> list = partnerCodeService.selectLowerCodeByCodeUpperForPartnerCodeMng(partnerCodeRequest);

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
    public ApiResponse<ApiResultCode> savePartnerCodes(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(description = "코드 등록 Request") @RequestBody PartnerCodeRequest.Create codeRequest
    ) {
        partnerCodeService.savePartnerCodes(codeRequest, jwtUser);
        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }



    /**
     * 코드_콤보_조회 (by CodeUpper)
     *
     * @param codeRequest
     * @return
     */
    @PostMapping(value = "/updatePartnerCode")
    @Operation(summary = "코드정보 변경 단건")
    public ApiResponse updatePartnerCode(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @RequestBody @Parameter(description = "코드 변경 Request") PartnerCodeRequest.UpdatePartnerCodeVal codeRequest
    ) {
        // 필수값 체크
        if (StringUtils.isEmpty(codeRequest.getCodeUpper())) {
            return new ApiResponse<>(ApiResultCode.NO_REQUIRED_VALUE, "파트너상위코드가 입력되지 않았습니다.");
        }

        User user = userService.selectUserById(jwtUser.getId());
        if (user.getPartnerId() == null || user.getPartnerId() == 0) {
            return new ApiResponse<>(ApiResultCode.NO_REQUIRED_VALUE, "로그인정보에 파트너 정보가 없습니다.");
        }

        codeRequest.setPartnerId(user.getPartnerId());

        // 코드_콤보_조회 (by CodeUpper)
        PartnerCode partnerCode = partnerCodeService.selectPartnerCodeByUk(codeRequest.getPartnerId(),codeRequest.getCodeUpper(), codeRequest.getCodeCd());
        // insert
        if (partnerCode == null) {
            partnerCode = new PartnerCode();
            partnerCode.setPartnerId(codeRequest.getPartnerId());
            partnerCode.setCodeUpper(codeRequest.getCodeUpper());
            partnerCode.setCodeCd(codeRequest.getCodeCd());
            partnerCode.setCodeNm(codeRequest.getCodeNm());
            partnerCode.setCodeOrder(1); // 일단 1번으로 등록
            partnerCodeService.insertPartnerCode(partnerCode);
        //update
        } else {
            partnerCode.setCodeNm(codeRequest.getCodeNm());
            partnerCodeService.updatePartnerCode(partnerCode);
        }

        return new ApiResponse<>(ApiResultCode.SUCCESS);
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
    public ApiResponse deletePartnerCode(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(description = "코드 수정 Request") @RequestBody PartnerCodeRequest.Delete codeRequest
    ) {
        // 코드_조회 (by Uk)
        Integer updateCount = partnerCodeService.deleteCode(codeRequest);

        if (updateCount == 0) {
            return new ApiResponse<>(ApiResultCode.FAIL_DELETE);
        }

        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }

    @AccessLog("파트너코드 소프트삭제")
    @PutMapping("/update-status")
    @Operation(summary = "파트너코드 소프트삭제")
    public ApiResponse updatePartnerCodeToDeletedStatus(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(description = "코드 삭제상태 수정 Request")  @RequestBody PartnerCodeRequest.SoftDelete codeRequest
    ) {
        return new ApiResponse<>(ApiResultCode.SUCCESS, partnerCodeService.updatePartnerCodeToDeletedStatus(codeRequest, jwtUser));
    }
}
