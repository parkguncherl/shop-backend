package com.shop.api.frontWeb.controller;

import com.shop.api.annotation.AccessLog;
import com.shop.api.annotation.JwtUser;
import com.shop.api.frontWeb.service.DisplayService;
import com.shop.api.product.service.ProductMngService;
import com.shop.core.annotations.NotAuthRequired;
import com.shop.core.biz.system.vo.request.PartnerCodeRequest;
import com.shop.core.biz.system.vo.response.ApiResponse;
import com.shop.core.biz.system.vo.response.PartnerCodeResponse;
import com.shop.core.entity.User;
import com.shop.core.enums.ApiResultCode;
import com.shop.core.product.vo.request.ProductMngRequest;
import com.shop.core.product.vo.response.ProductMngResponse;
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
 * Description: ProductMng Controller
 * Date: 2026/03/09
 * Author: park junsung
 * </pre>
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/frontWeb-display")
@Tag(name = "DisplayController", description = "상품관리 관련 API 정의")
public class DisplayController {

    private final DisplayService displayService;

    /**
     * 상품목록 조회
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
        List<PartnerCodeResponse.PartnerCodeDropDown> response = displayService.selectLowerCodeByPartnerCodeUpper(partnerCodeRequest);
        return new ApiResponse<>(ApiResultCode.SUCCESS, response);
    }
}
