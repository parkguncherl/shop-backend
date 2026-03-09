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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * 상품목록 조회(페이징)
     *
     * @param productInfoFilter
     * @return 페이징 동작에 대응하여 조회된 ProductInfoList
     */
    @AccessLog("상품컨텐츠목록 조회(페이징)")
    @GetMapping(value = "/productContentListPaging")
    @Operation(summary = "상품컨텐츠목록 조회(페이징)")
    public ApiResponse<PageResponse<ProductMngResponse.ProductInfo>> selectProdInfoList(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(name = "ProductMngRequestProductInfoFilter", description = "상품컨텐츠목록 조회 필터", in = ParameterIn.QUERY) ProductMngRequest.ProductInfoFilter productInfoFilter,
            @Parameter(name = "PageRequest", description = "상품컨텐츠목록 조회 페이징") PageRequest<ProductMngRequest.ProductInfoFilter> pageRequest
    ) {
        pageRequest.setFilter(productInfoFilter);
        PageResponse<ProductMngResponse.ProductInfo> response = productMngService.selectProdInfoList(pageRequest, jwtUser);
        return new ApiResponse<>(ApiResultCode.SUCCESS, response);
    }
}
