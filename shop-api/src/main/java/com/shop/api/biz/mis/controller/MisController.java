package com.shop.api.biz.mis.controller;

import com.shop.api.annotation.AccessLog;
import com.shop.api.annotation.JwtUser;
import com.shop.api.biz.mis.service.MisService;
import com.shop.core.biz.mis.vo.request.MisRequest;
import com.shop.core.biz.mis.vo.response.MisResponse;
import com.shop.core.biz.system.vo.response.ApiResponse;
import com.shop.core.entity.User;
import com.shop.core.enums.ApiResultCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mis")
@Tag(name = "MisController", description = "MIS 관리 API")
@RequiredArgsConstructor
public class MisController {

    private final MisService misService;

    @AccessLog("MIS 상품 분석 목록 조회")
    @GetMapping("/productViewList")
    @Operation(summary = "MIS 상품 분석 목록 조회 (기간별)")
    public ApiResponse<List<MisResponse.ProductViewItem>> getProductViewList(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @ModelAttribute MisRequest.ListFilter filter
    ) {
        filter.setPartnerId(jwtUser.getPartnerId());
        return new ApiResponse<>(ApiResultCode.SUCCESS, misService.getProductViewList(filter));
    }

    @AccessLog("MIS 판매 실적 목록 조회")
    @GetMapping("/salesStatList")
    @Operation(summary = "MIS 판매 실적 조회 (월별/주차별)")
    public ApiResponse<List<MisResponse.SalesStatItem>> getSalesStatList(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @ModelAttribute MisRequest.SalesStatFilter filter
    ) {
        filter.setPartnerId(jwtUser.getPartnerId());
        return new ApiResponse<>(ApiResultCode.SUCCESS, misService.getSalesStatList(filter));
    }

    @AccessLog("MIS 판매 실적 상세 목록 조회")
    @GetMapping("/salesStatDetailList")
    @Operation(summary = "MIS 판매 실적 상세 조회 (특정 월/주차 상품 분석)")
    public ApiResponse<List<MisResponse.ProductViewItem>> getSalesStatDetailList(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @ModelAttribute MisRequest.SalesStatDetailFilter filter
    ) {
        filter.setPartnerId(jwtUser.getPartnerId());
        return new ApiResponse<>(ApiResultCode.SUCCESS, misService.getSalesStatDetailList(filter));
    }

    @AccessLog("MIS 카테고리 분석 목록 조회")
    @GetMapping("/categoryViewList")
    @Operation(summary = "MIS 카테고리별 판매 분석 조회")
    public ApiResponse<List<MisResponse.CategoryViewItem>> getCategoryViewList(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @ModelAttribute MisRequest.CategoryViewFilter filter
    ) {
        filter.setPartnerId(jwtUser.getPartnerId());
        return new ApiResponse<>(ApiResultCode.SUCCESS, misService.getCategoryViewList(filter));
    }
}
