package com.shop.api.frontWeb.controller;

import com.shop.api.annotation.AccessLog;
import com.shop.api.annotation.GuestUser;
import com.shop.api.frontWeb.service.ProductService;
import com.shop.core.annotations.NotAuthRequired;
import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.biz.system.vo.response.ApiResponse;
import com.shop.core.entity.GuestToken;
import com.shop.core.enums.ApiResultCode;
import com.shop.core.frontWeb.vo.request.ProductRequest;
import com.shop.core.frontWeb.vo.response.ProductResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 * Description: frontWeb 이하 상품 Controller
 * Date: 2026/05/13
 * Author: park junsung
 * </pre>
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/frontWeb/product")
@Tag(name = "ProductController", description = "frontWeb 이하 상품 관련 API")
public class ProductController {

    private final ProductService productService;

    /**
     * frontWeb 이하 상품 목록 조회
     *
     * @param productInfoListFilter
     * @return 조회된 ProductInfo 목록 페이징
     */
    @AccessLog("frontWeb 이하 상품 목록 조회(페이징)")
    @GetMapping(value = "/productInfoListPaging")
    @Operation(summary = "frontWeb 이하 상품 목록 조회(페이징)")
    @NotAuthRequired
    public ApiResponse<PageResponse<ProductResponse.ProductInfo>> selectProductInfoListPaging(
            @Parameter(hidden = true) @GuestUser GuestToken guestUser,
            @Parameter(name = "ProductRequestProductInfoListFilter", description = "상품 목록 필터", in = ParameterIn.QUERY) ProductRequest.ProductInfoListFilter productInfoListFilter,
            @Parameter(name = "PageRequest", description = "상품 목록 조회 페이징") PageRequest<ProductRequest.ProductInfoListFilter> pageRequest
    ) {
        productInfoListFilter.setPartnerId(guestUser.getPartnerId());
        pageRequest.setFilter(productInfoListFilter);
        PageResponse<ProductResponse.ProductInfo> response = productService.selectProductInfoListPaging(pageRequest);
        return new ApiResponse<>(ApiResultCode.SUCCESS, response);
    }
}
