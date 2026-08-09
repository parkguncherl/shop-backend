package com.shop.api.biz.platform.controller;

import com.shop.api.annotation.AccessLog;
import com.shop.api.annotation.JwtUser;
import com.shop.api.biz.platform.service.PlatformProductService;
import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.biz.platform.vo.request.PlatformProductRequest;
import com.shop.core.biz.system.vo.response.ApiResponse;
import com.shop.core.entity.User;
import com.shop.core.enums.ApiResultCode;
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
 * Description: 플랫폼(DDM CHOICE) 상품 조회 Controller
 *   - 카테고리 조회조건: 대분류(majorCode)는 prod_type_code LIKE 접두, 소분류(minorCode)는 정확 일치(=)
 * </pre>
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/platform/product")
@Tag(name = "PlatformProductController", description = "플랫폼(DDM CHOICE) 상품 조회 API")
public class PlatformProductController {

    private final PlatformProductService platformProductService;

    /**
     * 플랫폼 상품 목록 조회 (대분류 LIKE / 소분류 EQUAL)
     *
     * @param jwtUser     로그인 사용자(partnerId 취득)
     * @param filter      majorCode(대분류 LIKE) / minorCode(소분류 =) 필터
     * @param pageRequest 페이징
     * @return ProductInfo 목록 페이징
     */
    @AccessLog("플랫폼 상품 목록 조회(대분류 LIKE / 소분류 EQUAL)")
    @GetMapping(value = "/productInfoListPaging")
    @Operation(summary = "플랫폼 상품 목록 조회 - 대분류(majorCode) LIKE 접두 / 소분류(minorCode) 정확 일치")
    public ApiResponse<PageResponse<ProductResponse.ProductInfo>> selectProductInfoListPaging(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(name = "PlatformProductRequestProductListFilter", description = "상품 목록 필터", in = ParameterIn.QUERY) PlatformProductRequest.ProductListFilter filter,
            @Parameter(name = "PageRequest", description = "상품 목록 조회 페이징") PageRequest<PlatformProductRequest.ProductListFilter> pageRequest
    ) {
        filter.setPartnerId(jwtUser.getPartnerId());
        pageRequest.setFilter(filter);
        PageResponse<ProductResponse.ProductInfo> response = platformProductService.selectProductInfoListPaging(pageRequest);
        return new ApiResponse<>(ApiResultCode.SUCCESS, response);
    }
}
