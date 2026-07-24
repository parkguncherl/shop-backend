package com.shop.api.biz.vendorProduct.controller;

import com.shop.api.annotation.AccessLog;
import com.shop.api.annotation.JwtUser;
import com.shop.core.biz.system.vo.response.ApiResponse;
import com.shop.core.biz.vendorProduct.dao.VendorProductDao;
import com.shop.core.biz.vendorProduct.vo.request.VendorProductRequest;
import com.shop.core.biz.vendorProduct.vo.response.VendorProductResponse;
import com.shop.core.entity.User;
import com.shop.core.enums.ApiResultCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendorProductMng")
@Tag(name = "VendorProductController", description = "협력업체별 상품 조회 API")
@RequiredArgsConstructor
public class VendorProductController {

    private final VendorProductDao vendorProductDao;

    @AccessLog("협력업체 상품 목록 조회")
    @GetMapping("/list")
    @Operation(summary = "협력업체(vendorId)별 상품 목록 조회")
    public ApiResponse<List<VendorProductResponse>> getList(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @RequestParam Integer vendorId
    ) {
        VendorProductRequest.Filter filter = new VendorProductRequest.Filter();
        filter.setVendorId(vendorId);
        filter.setPartnerId(jwtUser.getPartnerId());
        return new ApiResponse<>(ApiResultCode.SUCCESS, vendorProductDao.selectVendorProductList(filter));
    }
}
