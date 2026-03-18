package com.shop.api.product.controller;

import com.shop.api.annotation.AccessLog;
import com.shop.api.annotation.JwtUser;
import com.shop.api.product.service.ProductMngService;
import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.biz.system.vo.response.ApiResponse;
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
@RequestMapping("/productMng")
@Tag(name = "ProductMngController", description = "상품관리 관련 API 정의")
public class ProductMngController {

    private final ProductMngService productMngService;

    /**
     * 상품목록 조회
     *
     * @param productInfoFilter
     * @return 조회된 ProductInfoList
     */
    @AccessLog("상품목록 조회")
    @GetMapping(value = "/productInfoList")
    @Operation(summary = "상품목록 조회")
    public ApiResponse<List<ProductMngResponse.ProductInfo>> selectProdInfoList(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(name = "ProductMngRequestProductInfoFilter", description = "상품목록 조회 필터", in = ParameterIn.QUERY) ProductMngRequest.ProductInfoFilter productInfoFilter
    ) {
        List<ProductMngResponse.ProductInfo> response = productMngService.selectProdInfoList(productInfoFilter, jwtUser);
        return new ApiResponse<>(ApiResultCode.SUCCESS, response);
    }

    /**
     * 상품상세목록 조회
     *
     * @param productDetInfoFilter
     * @return 조회된 상품상세목록
     */
    @AccessLog("상품상세목록 조회")
    @GetMapping(value = "/productDetInfoList")
    @Operation(summary = "상품상세목록 조회")
    public ApiResponse<List<ProductMngResponse.ProductDetInfo>> selectProdDetInfo(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(name = "ProductMngRequestProductInfoFilter", description = "상품상세목록 조회 필터", in = ParameterIn.QUERY) ProductMngRequest.ProductDetInfoFilter productDetInfoFilter
    ) {
        List<ProductMngResponse.ProductDetInfo> response = productMngService.selectProdDetInfo(productDetInfoFilter, jwtUser);
        return new ApiResponse<>(ApiResultCode.SUCCESS, response);
    }

    /**
     * 상품정보 및 상품상세정보 추가(혹은 product 식별자(id) 가 주어질 시 상품상세정보 추가)
     *
     * @param insertProductInfo
     * @return
     */
    @AccessLog("상품정보 및 상품상세정보 추가")
    @PutMapping(value = "/insertProductInfo")
    @Operation(summary = "상품정보 및 상품상세정보 추가")
    public ApiResponse<Void> InsertProductInfo(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @RequestBody ProductMngRequest.InsertProductInfo insertProductInfo
    ) {
        productMngService.InsertProductInfo(insertProductInfo, jwtUser);
        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }
}
