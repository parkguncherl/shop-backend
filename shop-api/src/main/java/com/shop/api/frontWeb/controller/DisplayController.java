package com.shop.api.frontWeb.controller;

import com.shop.api.annotation.AccessLog;
import com.shop.api.biz.system.service.PartnerCodeService;
import com.shop.api.frontWeb.service.DisplayService;
import com.shop.core.annotations.NotAuthRequired;
import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.biz.system.vo.request.PartnerCodeRequest;
import com.shop.core.biz.system.vo.response.ApiResponse;
import com.shop.core.biz.system.vo.response.PartnerCodeResponse;
import com.shop.core.enums.ApiResultCode;
import com.shop.core.frontWeb.vo.request.DisplayRequest;
import com.shop.core.frontWeb.vo.response.DisplayResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <pre>
 * Description: frontWeb Display Controller
 * Date: 2026/04/24
 * Author: park junsung
 * </pre>
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/frontWeb/display")
@Tag(name = "DisplayController", description = "frontWeb 메인 페이지 관련 API 정의")
public class DisplayController {

    private final DisplayService displayService;
    private final PartnerCodeService partnerCodeService;



    /**
     * 상품목록 조회 비인증
     *
     * @param partnerUpperCode
     * @return 조회된 ProductInfoList
     */
    @AccessLog("web-카테고리목록 조회")
    @GetMapping(value = "/category-list/{partnerUpperCode}")
    @Operation(summary = "web-카테고리목록 조회")
    @NotAuthRequired
    public ApiResponse<List<PartnerCodeResponse.PartnerCodeDropDown>> categoryList(@PathVariable String partnerUpperCode) {
        PartnerCodeRequest.PartnerCodeDropDown partnerCodeRequest = new PartnerCodeRequest.PartnerCodeDropDown();
        partnerCodeRequest.setCodeUpper(partnerUpperCode);
        partnerCodeRequest.setPartnerId(1);
        List<PartnerCodeResponse.PartnerCodeDropDown> response = partnerCodeService.selectLowerCodeByPartnerCodeUpper(partnerCodeRequest);
        return new ApiResponse<>(ApiResultCode.SUCCESS, response);
    }

    /**
     * frontWeb 메인페이지 상품정보 목록 조회
     *
     * @param productInfoListFilter
     * @return 조회된 ProductInfoList
     */
    @AccessLog("frontWeb 메인 페이지 영역 상품정보 목록 조회")
    @GetMapping(value = "/productInfoListForEnum")
    @Operation(summary = "frontWeb 메인 페이지 영역 상품정보 목록 조회")
    @NotAuthRequired
    public ApiResponse<PageResponse<DisplayResponse.ProductInfoForEnum>> selectProductInfoListForEnumPaging(
            @Parameter(name = "DisplayRequestProductInfoListFilter", description = "메인페이지 상품정보 목록 필터", in = ParameterIn.QUERY) DisplayRequest.ProductInfoListFilter productInfoListFilter,
            @Parameter(name = "PageRequest", description = "상품정보 목록 조회 페이징") PageRequest<DisplayRequest.ProductInfoListFilter> pageRequest
    ) {
        pageRequest.setFilter(productInfoListFilter);
        PageResponse<DisplayResponse.ProductInfoForEnum> response = displayService.selectProductInfoListForEnumPaging(pageRequest);
        return new ApiResponse<>(ApiResultCode.SUCCESS, response);
    }
}
