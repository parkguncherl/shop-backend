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
}
