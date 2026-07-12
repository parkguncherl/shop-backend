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
import org.springframework.web.bind.annotation.RequestParam;

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

    /**
     * frontWeb 이하 상품 목록 조회 (categoryId 필수 버전)
     *
     * @param productInfoListFilter categoryId 필수
     * @return 조회된 ProductInfo 목록 페이징
     */
    @AccessLog("frontWeb 이하 상품 목록 조회(카테고리 필수)")
    @GetMapping(value = "/productInfoListByCategory")
    @Operation(summary = "frontWeb 이하 상품 목록 조회(카테고리 필수)")
    @NotAuthRequired
    public ApiResponse<PageResponse<ProductResponse.ProductInfo>> selectProductInfoListByCategory(
            @Parameter(hidden = true) @GuestUser GuestToken guestUser,
            @Parameter(name = "ProductRequestProductInfoListFilter", description = "상품 목록 필터(categoryId 필수)", in = ParameterIn.QUERY) ProductRequest.ProductInfoListFilter productInfoListFilter,
            @Parameter(name = "PageRequest", description = "상품 목록 조회 페이징") PageRequest<ProductRequest.ProductInfoListFilter> pageRequest
    ) {
        productInfoListFilter.setPartnerId(guestUser.getPartnerId());
        pageRequest.setFilter(productInfoListFilter);
        PageResponse<ProductResponse.ProductInfo> response = productService.selectProductInfoListByCategory(pageRequest);
        return new ApiResponse<>(ApiResultCode.SUCCESS, response);
    }

    /**
     * 상품 검색 (상품명 + 색상)
     *
     * @param productSearchFilter keyword, lastId
     * @return 검색된 ProductInfo 목록 페이징
     */
    @AccessLog("frontWeb 이하 상품 검색")
    @GetMapping(value = "/productSearch")
    @Operation(summary = "frontWeb 이하 상품 검색 (상품명 + 색상)")
    @NotAuthRequired
    public ApiResponse<PageResponse<ProductResponse.ProductInfo>> selectProductSearchList(
            @Parameter(hidden = true) @GuestUser GuestToken guestUser,
            @Parameter(name = "ProductRequestProductSearchFilter", description = "상품 검색 필터", in = ParameterIn.QUERY) ProductRequest.ProductSearchFilter productSearchFilter,
            @Parameter(name = "PageRequest", description = "상품 검색 페이징") PageRequest<ProductRequest.ProductSearchFilter> pageRequest
    ) {
        productSearchFilter.setPartnerId(guestUser.getPartnerId());
        pageRequest.setFilter(productSearchFilter);
        PageResponse<ProductResponse.ProductInfo> response = productService.selectProductSearchList(pageRequest);
        return new ApiResponse<>(ApiResultCode.SUCCESS, response);
    }

    /**
     * 상품 상세 조회
     *
     * @param productId 상품 id
     * @return ProductDetail (상품정보 + SKU목록 + 연관상품목록)
     */
    @AccessLog("frontWeb 이하 상품 상세 조회")
    @GetMapping(value = "/productDetail")
    @Operation(summary = "frontWeb 이하 상품 상세 조회")
    @NotAuthRequired
    public ApiResponse<ProductResponse.ProductDetail> selectProductDetail(
            @Parameter(hidden = true) @GuestUser GuestToken guestUser,
            @Parameter(name = "productId", description = "상품 id", in = ParameterIn.QUERY) @RequestParam Integer productId
    ) {
        ProductRequest.ProductDetailParam param = new ProductRequest.ProductDetailParam();
        param.setProductId(productId);
        param.setPartnerId(guestUser.getPartnerId());

        ProductResponse.ProductDetail detail = productService.selectProductDetail(param);
        if (detail == null) {
            return new ApiResponse<>(ApiResultCode.DATA_NOT_FOUND);
        }
        return new ApiResponse<>(ApiResultCode.SUCCESS, detail);
    }
}
