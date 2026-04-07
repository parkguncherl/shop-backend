package com.shop.api.biz.partner.controller;

import com.shop.api.annotation.AccessLog;
import com.shop.api.annotation.JwtUser;
import com.shop.api.biz.mypage.service.MypageService;
import com.shop.api.biz.partner.service.PartnerService;
import com.shop.api.biz.system.service.UserService;
import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.biz.partner.vo.request.PartnerRequest;
import com.shop.core.biz.partner.vo.response.PartnerResponse;
import com.shop.core.biz.system.vo.response.ApiResponse;
import com.shop.core.entity.Partner;
import com.shop.core.entity.User;
import com.shop.core.enums.ApiResultCode;
import com.shop.core.exception.CustomRuntimeException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/partner")
@Tag(name = "PartnerController", description = "화주계정 관련 API")
public class PartnerController {

    private final PartnerService partnerService;
    private final MypageService mypageService;
    private final UserService userService;

    /**
     * 내파트너만 조회하기 지우지 말것 2025-03-12
     */

    @AccessLog("자기 파트너 정보 조회")
    @GetMapping(value = "/my-partner")
    @Operation(summary = "자기 파트너 정보 조회")
    public ApiResponse<PartnerResponse.Select> selectMyPartner(@Parameter(hidden = true) @JwtUser User jwtUser) {
        PartnerResponse.Select response = partnerService.selectMyPartnerByLoginId(jwtUser.getLoginId());
        return new ApiResponse<>(ApiResultCode.SUCCESS, response);
    }

    
    /**
     * 화주관리_목록_조회 (리스트)
     *
     * @return
     */
    @AccessLog("화주관리 목록 조회")
    @GetMapping(value = "/my-partners/{logisId}")
    @Operation(summary = "화주관리 목록 조회 (리스트)")
    public ApiResponse<List<PartnerResponse.Select>> selectPartnerList(@PathVariable Integer logisId){
        // 화주 목록 조회
        List<PartnerResponse.Select> partnerList = partnerService.selectPartnerList(logisId);
        return new ApiResponse<>(ApiResultCode.SUCCESS, partnerList);
    }


    /**
     * 화주관리_목록_조회 (페이징)
     *
     * @param filter
     * @param pageRequest
     * @return
     */
    @AccessLog("화주관리 목록 조회")
    @GetMapping(value = "/paging")
    @Operation(summary = "화주관리 목록 조회 (페이징)")
    public ApiResponse<PageResponse<PartnerResponse.Paging>> selectPartnerPaging(
            @Parameter(name = "PartnerRequestPagingFilter", description = "화주관리 목록 조회 (페이징) 필터", in = ParameterIn.PATH) PartnerRequest.PagingFilter filter,
            @Parameter(name = "PageRequest", description = "화주관리 목록 조회 페이징") PageRequest<PartnerRequest.PagingFilter> pageRequest
    ){

        pageRequest.setFilter(filter);
        PageResponse<PartnerResponse.Paging> response = partnerService.selectPartnerPaging(pageRequest);

        return new ApiResponse<>(ApiResultCode.SUCCESS, response);
    }

    /**
     * 화주관리_목록_조회 (검색)
     *
     * @return
     */
    @AccessLog("(화주관리) 목록 조회")
    @GetMapping(value = "/list")
    @Operation(summary = "(화주관리) 목록 조회 (검색)")
    public ApiResponse<List<PartnerResponse.ForSearching>> selectPartnerListForSearching(
            @Parameter(name = "PartnerRequestFilterForList", description = "(화주관리) 목록 검색 필터") PartnerRequest.FilterForList filterForList,
            @Parameter(hidden = true) @JwtUser User jwtUser
    ){
        // 화주 목록 조회
        List<PartnerResponse.ForSearching> partnerList = partnerService.selectPartnerListForSearching(filterForList, jwtUser);
        return new ApiResponse<>(ApiResultCode.SUCCESS, partnerList);
    }

    /**
     *
     *
     */
    /**
     * 화주_조회 (by Id)
     *
     * @return
     */
    @AccessLog("화주 조회")
    @GetMapping(value = "/detail")
    @Operation(summary = "화주_조회 (by Id)")
    public ApiResponse<Partner> selectPartnerById(@Parameter(hidden = true) @JwtUser User jwtUser){
        // 화주 조회
        User user = userService.selectUserById(jwtUser.getId());
        Partner response = partnerService.selectPartnerById(user.getPartnerId());
        return new ApiResponse<>(ApiResultCode.SUCCESS, response);
    }


    /**
     * 화주_조회 (by Id)
     *
     * @return
     */
    @AccessLog("화주 조회 관리자")
    @GetMapping(value = "/detail/{partnerId}")
    @Operation(summary = "화주_조회 (by Id)")
    public ApiResponse<Partner> selectPartnerById(@PathVariable Integer partnerId){
        // 화주 조회
        Partner response = partnerService.selectPartnerById(partnerId);
        return new ApiResponse<>(ApiResultCode.SUCCESS, response);
    }

    /**
     * 화주_등록
     * @param jwtUser
     * @param partnerRequest
     * @return
     */
    @AccessLog("화주 등록")
    @PostMapping()
    @Operation(summary = "화주 등록")
    public ApiResponse<ApiResultCode> insertPartner(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(description = "화주 등록 Request") @RequestBody PartnerRequest.Create partnerRequest
    ) {
        // 필수값 체크
        if (StringUtils.isEmpty(partnerRequest.getPartnerNm())
                || StringUtils.isEmpty(partnerRequest.getPartnerTicker())
                || StringUtils.isEmpty(partnerRequest.getPhoneNo())){
            throw new CustomRuntimeException(ApiResultCode.NO_REQUIRED_VALUE);
        }

        // 화주 등록
        Integer insertCount = partnerService.insertPartner(partnerRequest, jwtUser);

        if (insertCount == 0) {
            throw new CustomRuntimeException(ApiResultCode.FAIL_CREATE);
        }

        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }

    /**
     * 화주_수정
     *
     * @param jwtUser
     * @param partnerRequest
     * @return
     */
    @AccessLog("화주 수정")
    @PutMapping()
    @Operation(summary = "화주 수정")
    public ApiResponse updatePartner(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(description = "화주 수정 Request") @RequestBody PartnerRequest.Update partnerRequest
    ) {
        // 필수값 체크
        if (StringUtils.isEmpty(partnerRequest.getPartnerNm())
                || StringUtils.isEmpty(partnerRequest.getPartnerTicker())
                || StringUtils.isEmpty(partnerRequest.getPhoneNo())) {
            throw new CustomRuntimeException(ApiResultCode.NO_REQUIRED_VALUE);
        }

        // 실존하는 데이터인지 체크
        Partner partnerResponse = partnerService.selectPartnerById(partnerRequest.getId()); // ID로 조회
        if(partnerResponse == null){
            throw new CustomRuntimeException(ApiResultCode.NOT_FOUND_USER);
        }

        // 화주 수정
        Integer updateCount = partnerService.updatePartner(partnerRequest, jwtUser);
        if (updateCount == 0) {
            throw new CustomRuntimeException(ApiResultCode.FAIL_UPDATE);
        }

        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }


    /**
     * 화주_삭제
     *
     * @param partnerRequest
     * @return
     */
    @AccessLog("화주 삭제")
    @DeleteMapping()
    @Operation(summary = "화주 삭제")
    public ApiResponse<ApiResultCode> deletePartner(
            @Parameter(description = "화주 삭제 Request") @RequestBody PartnerRequest.Delete partnerRequest
    ) {
        // 필수값 체크
        if (StringUtils.isEmpty(partnerRequest.getId().toString())) {
            throw new CustomRuntimeException(ApiResultCode.NO_REQUIRED_VALUE);
        }
        partnerService.deletePartner(partnerRequest);
        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }

}
